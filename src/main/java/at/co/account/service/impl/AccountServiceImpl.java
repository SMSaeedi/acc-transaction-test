package at.co.account.service.impl;

import at.co.account.dto.AccountDto;
import at.co.account.dto.AccountUpdateDto;
import at.co.account.entity.AccountEntity;
import at.co.account.enums.AccountType;
import at.co.account.enums.TransactionType;
import at.co.account.exception.Errors;
import at.co.account.exception.NotAllowedException;
import at.co.account.exception.NotFoundException;
import at.co.account.repository.AccountRepository;
import at.co.account.service.AccountService;
import at.co.account.service.CreditService;
import at.co.account.service.CustomerService;
import at.co.account.service.DebitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CreditService creditService;
    private final DebitService debitService;
    private final CustomerService customerService;
    private Random random = new Random();

    @Override
    public AccountEntity createAccount(Long customerId, AccountDto accountDto) {
        var accountEntity = AccountEntity.builder().build();

        var customerEntity = customerService.findCustomerById(customerId);

        if (accountDto.getInitialCredit().equals(0D))
            throw new NotAllowedException(Errors.NOT_ALLOWED);

        var findByType = hasAccount(accountDto.getAccountType());
        if (!findByType.isPresent()) {
            var accNumber = Long.valueOf(String.format("%08d", random.nextInt(100000000)));
            accountEntity = AccountEntity.builder()
                    .accNr(accNumber)
                    .initialCredit(accountDto.getInitialCredit())
                    .balance(accountDto.getInitialCredit())
                    .accountType(accountDto.getAccountType())
                    .build();
        } else {
            accountEntity = findByType.get();
            if (accountEntity.getAccountType().equals(accountDto.getAccountType())) {
                var balance = accountEntity.getBalance();
                accountDto.setInitialCredit(balance + accountDto.getInitialCredit());
            }
        }
        creditService.credit(customerEntity, accountEntity);

        return accountEntity;
    }

    @Override
    public AccountEntity updateAccount(Long customerId, AccountUpdateDto accountUpdateDto) {
        var accountEntity = AccountEntity.builder().build();

        var customerEntity = customerService.findCustomerById(customerId);

        if (accountUpdateDto.getInitialCredit().equals(0D))
            throw new NotAllowedException(Errors.NOT_ALLOWED);

        var findByType = hasAccount(accountUpdateDto.getAccountType());
        if (!findByType.isPresent()) {
            var accNumber = Long.valueOf(String.format("%08d", random.nextInt(100000000)));
            accountEntity = AccountEntity.builder()
                    .accNr(accNumber)
                    .initialCredit(accountUpdateDto.getInitialCredit())
                    .balance(accountUpdateDto.getInitialCredit())
                    .accountType(accountUpdateDto.getAccountType())
                    .build();
        } else {
            accountEntity = findByType.get();
            if (accountEntity.getAccountType().equals(accountUpdateDto.getAccountType())) {
                var balance = accountEntity.getBalance();
                accountUpdateDto.setInitialCredit(balance + accountUpdateDto.getInitialCredit());
            }
        }

        if (accountUpdateDto.getTransactionType().equals(TransactionType.CREDIT))
            creditService.credit(customerEntity, accountEntity);
        else if (accountUpdateDto.getTransactionType().equals(TransactionType.DEBIT))
            debitService.debit(customerEntity, accountEntity);

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
}