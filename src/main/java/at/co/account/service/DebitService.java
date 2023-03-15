package at.co.account.service;

import at.co.account.entity.AccountEntity;
import at.co.account.entity.CustomerEntity;
import at.co.account.entity.TransactionEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
public interface DebitService {
    @Transactional(propagation = Propagation.REQUIRED)
    TransactionEntity debit(CustomerEntity customerEntity, AccountEntity accountEntity, Double oldBalance);
}