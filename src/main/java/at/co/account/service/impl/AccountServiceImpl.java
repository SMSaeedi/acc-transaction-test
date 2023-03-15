package at.co.account.service.impl;

import at.co.account.dto.AccountDto;
import at.co.account.entity.AccountEntity;
import at.co.account.entity.TransactionEntity;
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

        var accNumber = Long.valueOf(String.format("%08d", random.nextInt(100000000)));
        var accountEntity = AccountEntity.builder()
                .accNr(accNumber)
                .balance(accountDto.getBalance())
                .accountType(accountDto.getAccountType())
                .build();
        var account = accountRepository.saveAndFlush(accountEntity);

        customerEntity.setAccountEntity(accountEntity);
        customerRepository.saveAndFlush(customerEntity);

        //in creating account first transaction must be applied here.
        var transactionEntity = TransactionEntity.builder()
                .initialCredit(accountDto.getBalance())
                .transactionType(TransactionType.CREDIT)
                .lastBalance(accountDto.getBalance())
                .customerId(customerEntity.getId())
                .customerEntity(customerEntity)
                .build();

        transactionEntity.setCustomerEntity(customerEntity);
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
    public Double getLastBalance(Long customerId, Long accNr, int accountType) {
        var accountEntity = accountRepository.findByAccountType(accountType)
                .orElseThrow(() -> new NotFoundException(Errors.NO_ACCOUNT_TYPE_FOUND));

        var allCustomerAccNrs = customerService.findAllAccNrs(customerId);

        return allCustomerAccNrs.stream()
                .filter(account -> account.getAccountType().equals(accountEntity.getAccountType()))
                .map(AccountEntity::getBalance).findFirst().orElse(0D);
    }
}