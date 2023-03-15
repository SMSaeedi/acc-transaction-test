package at.co.account.service.impl;

import at.co.account.dto.AccountDto;
import at.co.account.entity.AccountEntity;
import at.co.account.entity.TransactionEntity;
import at.co.account.enums.AccountType;
import at.co.account.enums.TransactionType;
import at.co.account.exception.Errors;
import at.co.account.exception.NotAllowedException;
import at.co.account.exception.NotFoundException;
import at.co.account.repository.AccountRepository;
import at.co.account.repository.CustomerRepository;
import at.co.account.repository.TransactionRepository;
import at.co.account.service.AccountService;
import at.co.account.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private Random random = new Random();

    @Override
    public AccountEntity createAccount(Long customerId, AccountDto accountDto) {
        var customerEntity = customerService.findCustomerById(customerId);

        if (accountDto.getBalance().equals(0D))
            throw new NotAllowedException(Errors.NOT_ALLOWED);

        var accountEntity = findAccountByType(accountDto.getAccountType());
        if (accountEntity.equals(accountDto.getAccountType())) {
            var balance = accountEntity.getBalance();
            accountDto.setBalance(balance + accountDto.getBalance());
        } else {
            var accNumber = Long.valueOf(String.format("%08d", random.nextInt(100000000)));
            accountEntity = AccountEntity.builder()
                    .accNr(accNumber)
                    .balance(accountDto.getBalance())
                    .accountType(accountDto.getAccountType())
                    .build();
        }
        var account = accountRepository.saveAndFlush(accountEntity);

        customerEntity.setAccountEntity(accountEntity);
        customerEntity.setAccountId(accountEntity.getId());
        customerRepository.saveAndFlush(customerEntity);

        //in creating account first transaction must be applied here.
        var transactionEntity = TransactionEntity.builder()
                .initialCredit(accountDto.getBalance())
                .transactionType(TransactionType.CREDIT)
                .isTransactionSucceeded(true)
                .lastBalance(accountDto.getBalance())
                .customerId(customerEntity.getId())
                .customerEntity(customerEntity)
                .build();

        transactionEntity.setCustomerEntity(customerEntity);
        transactionEntity.setCustomerId(customerEntity.getId());
        transactionRepository.saveAndFlush(transactionEntity);

        return account;
    }

    @Override
    public AccountEntity findAccountByNr(Long accNr) {
        var accountEntity = accountRepository.findByAccNr(accNr)
                .orElseThrow(() -> new NotFoundException(Errors.NO_SUCH_ACCOUNT_FOUND));

        return accountEntity;
    }

    @Override
    public AccountEntity findAccountByType(AccountType accType) {
        return accountRepository.findByAccountType(accType)
                .orElseThrow(() -> new NotFoundException(Errors.NO_ACCOUNT_TYPE_FOUND));
    }

    @Override
    public Double getLastBalance(Long customerId, Long accNr, AccountType accountType) {
        var accountEntity = findAccountByType(accountType);

        var allCustomerAccNrs = customerService.findAllAccNrs(customerId);

        return allCustomerAccNrs.stream()
                .filter(account -> account.getAccountType().equals(accountEntity.getAccountType()))
                .map(AccountEntity::getBalance).findFirst().orElse(0D);
    }
}