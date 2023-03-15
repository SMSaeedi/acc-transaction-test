package at.co.account.service.impl;

import at.co.account.entity.TransactionEntity;
import at.co.account.exception.Errors;
import at.co.account.exception.MismatchedInputException;
import at.co.account.repository.AccountRepository;
import at.co.account.repository.TransactionRepository;
import at.co.account.service.AccountService;
import at.co.account.service.CreditService;
import at.co.account.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
@RequiredArgsConstructor
@Slf4j
public class CreditServiceImpl implements CreditService {
    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Override
    public TransactionEntity credit(Long customerId, Long accNr, Double amount) {
        var customerEntity = customerService.findOneAccNr(customerId, accNr);
        if (!customerId.equals(customerEntity.getId()))
            throw new MismatchedInputException(Errors.CUSTOMER_NOT_FOUND);

        var accountEntity = accountService.findAccountByNr(accNr);

        Double lastBalance = accountService.getLastBalance(customerId, accNr, accountEntity.getAccountType().ordinal());
        accountEntity.setBalance(lastBalance + amount);
        accountRepository.save(accountEntity);

        var creditTransaction = TransactionEntity.builder()
                .customerId(customerEntity.getId())
                .customerEntity(customerEntity)
                .initialCredit(amount)
                .build();
        transactionRepository.save(creditTransaction);

        return creditTransaction;
    }
}