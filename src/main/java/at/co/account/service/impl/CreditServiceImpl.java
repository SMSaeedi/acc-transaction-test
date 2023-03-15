package at.co.account.service.impl;

import at.co.account.entity.AccountEntity;
import at.co.account.entity.CustomerEntity;
import at.co.account.entity.TransactionEntity;
import at.co.account.enums.TransactionType;
import at.co.account.exception.Errors;
import at.co.account.exception.NotFoundException;
import at.co.account.repository.AccountRepository;
import at.co.account.repository.CustomerRepository;
import at.co.account.repository.TransactionRepository;
import at.co.account.service.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class CreditServiceImpl implements CreditService {
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Override
    public TransactionEntity credit(CustomerEntity customerEntity, AccountEntity accountEntity) {
        if (!accountRepository.findByAccNr(accountEntity.getAccNr()).isPresent())
            throw new NotFoundException(Errors.NO_SUCH_ACCOUNT_FOUND);

        accountRepository.save(accountEntity);

        customerEntity.setAccountId(accountEntity.getId());
        customerEntity.setAccountEntity(accountEntity);
        customerRepository.save(customerEntity);

        var creditTransaction = TransactionEntity.builder()
                .transactionType(TransactionType.CREDIT)
                .isTransactionSucceeded(true)
                .oldBalance(accountEntity.getBalance())
                .newBalance(accountEntity.getInitialCredit() + accountEntity.getBalance())
                .customerId(customerEntity.getId())
                .customerEntity(customerEntity)
                .build();
        transactionRepository.saveAndFlush(creditTransaction);

        return creditTransaction;
    }
}