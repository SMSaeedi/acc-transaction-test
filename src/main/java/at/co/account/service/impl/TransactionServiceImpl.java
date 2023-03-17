package at.co.account.service.impl;

import at.co.account.entity.TransactionEntity;
import at.co.account.repository.TransactionRepository;
import at.co.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionEntity> allCustomersTransactions(Long customerId) {
        return transactionRepository.findAllByCustomerId(customerId);
    }
}
