package at.co.account.service;

import at.co.account.entity.AccountEntity;
import at.co.account.entity.CustomerEntity;
import at.co.account.entity.TransactionEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CreditService {
    TransactionEntity credit(CustomerEntity customerEntity, AccountEntity accountEntity, Double oldBalance);
}