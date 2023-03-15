package at.co.account.service;

import at.co.account.entity.TransactionEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Validated
public interface TransactionService {
    List<TransactionEntity> allCustomersTransactions(Long customerId);
    List<TransactionEntity> last_10_CustomersTransactions(Long customerId);
}
