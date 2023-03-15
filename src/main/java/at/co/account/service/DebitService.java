package at.co.account.service;

import at.co.account.entity.TransactionEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public interface DebitService {
    TransactionEntity debit(Long customerId, Long accNr, Double amount);
}