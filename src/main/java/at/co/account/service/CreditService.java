package at.co.account.service;

import at.co.account.entity.TransactionEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CreditService {
    TransactionEntity credit(Long customerId, Long accNr, Double amount);
}