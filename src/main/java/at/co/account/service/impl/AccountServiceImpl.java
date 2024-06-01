package at.co.account.service.impl;

import at.co.account.dto.AccountDto;
import at.co.account.dto.AccountUpdateDto;
import at.co.account.entity.AccountEntity;
import at.co.account.entity.CustomerEntity;
import at.co.account.entity.TransactionEntity;
import at.co.account.enums.AccountType;
import at.co.account.enums.CreditStatus;
import at.co.account.enums.DebitStatus;
import at.co.account.enums.TransactionType;
import at.co.account.exception.*;
import at.co.account.repository.AccountRepository;
import at.co.account.repository.CustomerRepository;
import at.co.account.repository.TransactionRepository;
import at.co.account.service.AccountService;
import at.co.account.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CreditService creditService;
    private final DebitService debitService;
    private final CustomerService customerService;
    private final Random random = new Random();

    @Override
    public AccountEntity createAccount(Long customerId, AccountDto accountDto) {
        var accountEntity = AccountEntity.builder().build();
        var oldBalance = 0D;

        var customerEntity = customerService.findCustomerById(customerId);

        if (accountDto.getAmount().equals(0D))
            throw new NotAllowedException(Errors.NOT_ALLOWED);

        var findByType = hasAccount(accountDto.getAccountType());
        if (findByType.isEmpty()) {
            var accNumber = Long.valueOf(String.format("%08d", random.nextInt(100000000)));
            accountEntity = AccountEntity.builder()
                    .accNr(accNumber)
                    .amount(accountDto.getAmount())
                    .balance(accountDto.getAmount())
                    .accountType(accountDto.getAccountType())
                    .build();
        } else {
            accountEntity = findByType.get();
            if (accountEntity.getAccountType().equals(accountDto.getAccountType())) {
                oldBalance = accountEntity.getBalance();
                accountEntity.setBalance(accountEntity.getBalance() + accountDto.getAmount());
                accountEntity.setAmount(accountDto.getAmount());
            }
        }
        try {
            creditService.credit(customerEntity, accountEntity, oldBalance);
        } catch (Throwable exception) {
            transactionRepository.save(TransactionEntity.builder()
                    .creditStatus(CreditStatus.FAIL)
                    .exceptionMsg(exception.getMessage())
                    .build());
        }

        return accountEntity;
    }

    @Override
    public AccountEntity updateAccount(Long customerId, AccountUpdateDto accountUpdateDto) {
        var accountEntity = AccountEntity.builder().build();
        var oldBalance = 0D;

        var customerEntity = customerService.findCustomerById(customerId);

        if (accountUpdateDto.getAmount().equals(0D))
            throw new NotAllowedException(Errors.NOT_ALLOWED);

        var findByType = hasAccount(accountUpdateDto.getAccountType());
        if (findByType.isEmpty()) {
            var accNumber = Long.valueOf(String.format("%08d", random.nextInt(100000000)));
            accountEntity = AccountEntity.builder()
                    .accNr(accNumber)
                    .amount(accountUpdateDto.getAmount())
                    .balance(accountUpdateDto.getAmount())
                    .accountType(accountUpdateDto.getAccountType())
                    .build();
        } else {
            accountEntity = findByType.get();
            if (accountEntity.getAccountType().equals(accountUpdateDto.getAccountType())) {
                oldBalance = accountEntity.getBalance();
                accountEntity.setAmount(accountUpdateDto.getAmount());
            }
        }
        try {
            if (accountUpdateDto.getTransactionType().equals(TransactionType.CREDIT)) {
                accountEntity.setBalance(accountEntity.getBalance() + accountUpdateDto.getAmount());
                creditService.credit(customerEntity, accountEntity, oldBalance);
            } else if (accountUpdateDto.getTransactionType().equals(TransactionType.DEBIT)) {
                accountEntity.setBalance(accountEntity.getBalance() - accountUpdateDto.getAmount());
                debitService.debit(customerEntity, accountEntity, oldBalance);
            }
        } catch (Throwable exception) {
            var transaction = TransactionEntity.builder().build();
            if (exception instanceof CreditException) {
                transaction = TransactionEntity.builder()
                        .creditStatus(CreditStatus.FAIL)
                        .exceptionMsg(exception.getMessage())
                        .build();
            } else if (exception instanceof DebitException) {
                transaction = TransactionEntity.builder()
                        .debitStatus(DebitStatus.FAIL)
                        .exceptionMsg(exception.getMessage())
                        .build();
            }
            transactionRepository.save(transaction);
        }

        return accountEntity;
    }

    @Override
    public AccountEntity findAccountByNr(Long accNr) {
        return accountRepository.findByAccNr(accNr)
                .orElseThrow(() -> new NotFoundException(Errors.NO_SUCH_ACCOUNT_FOUND));
    }

    @Override
    public Optional<AccountEntity> hasAccount(AccountType accType) {
        return accountRepository.findByAccountType(accType);
    }

    @Override
    public Double getLastBalance(Long customerId, Long accNr, AccountType accountType) {
        Optional<AccountEntity> accountEntity = hasAccount(accountType);

        var allCustomerAccNrs = customerService.findAllAccNrs(customerId);

        return accountEntity.map(entity -> allCustomerAccNrs.stream()
                .filter(account -> account.getAccountType().equals(entity.getAccountType()))
                .map(AccountEntity::getBalance).findFirst().orElse(0D)).orElse(0D);
    }

    @Service
    @Transactional(propagation = Propagation.REQUIRED)
    @RequiredArgsConstructor
    @Slf4j
    class CreditService {
        private final TransactionRepository transactionRepository;
        private final CustomerRepository customerRepository;
        private final AccountRepository accountRepository;

        public TransactionEntity credit(CustomerEntity customerEntity, AccountEntity accountEntity, Double oldBalance) {
            try {
                accountRepository.save(accountEntity);

                var byAccountNr = accountRepository.findByAccNr(accountEntity.getAccNr());
                if (byAccountNr.isEmpty())
                    throw new NotFoundException(Errors.NO_SUCH_ACCOUNT_FOUND);

                customerEntity.setAccountId(accountEntity.getId());
                customerEntity.setAccountEntity(accountEntity);
                customerRepository.save(customerEntity);

                var creditTransaction = TransactionEntity.builder()
                        .transactionType(TransactionType.CREDIT)
                        .creditStatus(CreditStatus.SUCCESS)
                        .oldBalance(oldBalance)
                        .newBalance(accountEntity.getBalance())
                        .customerId(customerEntity.getId())
                        .customerEntity(customerEntity)
                        .build();
                transactionRepository.saveAndFlush(creditTransaction);

                return creditTransaction;
            } catch (Throwable ex) {
                throw new CreditException(ex.getMessage(), ex);
            }
        }
    }

    @Service
    @Transactional(propagation = Propagation.REQUIRED)
    @RequiredArgsConstructor
    @Slf4j
    class DebitService {
        private final TransactionRepository transactionRepository;
        private final CustomerRepository customerRepository;
        private final AccountRepository accountRepository;

        public TransactionEntity debit(CustomerEntity customerEntity, AccountEntity accountEntity, Double oldBalance) {
            try {
                accountRepository.save(accountEntity);

                var byAccountNr = accountRepository.findByAccNr(accountEntity.getAccNr());
                if (byAccountNr.isEmpty())
                    throw new NotFoundException(Errors.NO_SUCH_ACCOUNT_FOUND);

                customerEntity.setAccountId(accountEntity.getId());
                customerEntity.setAccountEntity(accountEntity);
                customerRepository.save(customerEntity);

                var creditTransaction = TransactionEntity.builder()
                        .transactionType(TransactionType.DEBIT)
                        .debitStatus(DebitStatus.SUCCESS)
                        .oldBalance(oldBalance)
                        .newBalance(accountEntity.getBalance())
                        .customerId(customerEntity.getId())
                        .customerEntity(customerEntity)
                        .build();
                transactionRepository.saveAndFlush(creditTransaction);

                return creditTransaction;
            } catch (Exception ex) {
                throw new DebitException(ex.getMessage(), ex);
            }
        }
    }
}