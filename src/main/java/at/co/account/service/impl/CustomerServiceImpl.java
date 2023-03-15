package at.co.account.service.impl;

import at.co.account.dto.CustomerDto;
import at.co.account.dto.GetCustomerTransaction;
import at.co.account.dto.TransactionDto;
import at.co.account.entity.AccountEntity;
import at.co.account.entity.CustomerEntity;
import at.co.account.exception.Errors;
import at.co.account.exception.NotFoundException;
import at.co.account.repository.CustomerRepository;
import at.co.account.service.AccountService;
import at.co.account.service.CustomerService;
import at.co.account.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class})
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    @Override
    public GetCustomerTransaction getCustomerTransaction(Long customerId) {
        var customerEntity = findCustomerById(customerId);
        var customerDto = objectMapper.convertValue(customerEntity, CustomerDto.class);
        var accountEntity = accountService.findAccountByNr(customerEntity.getAccountId());

        var transactionEntities = transactionService.allCustomersTransactions(customerEntity.getId());
        var transactionDtos = List.of(objectMapper.convertValue(transactionEntities, TransactionDto[].class));

        return GetCustomerTransaction.builder()
                .customerName(customerDto.getName())
                .customerSirName(customerEntity.getSirName())
                .balance(customerEntity.getAccountEntity().getBalance())
                .transaction(transactionDtos)
                .build();
    }

    @Override
    public CustomerEntity findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(Errors.CUSTOMER_NOT_FOUND));
    }

    @Override
    public CustomerEntity findOneAccNr(Long customerId, Long accNr) {
        return customerRepository.findByIdAndAccountId(customerId, accNr)
                .orElseThrow(() -> new NotFoundException(Errors.NO_ACCOUNT_FOUND));
    }

    @Override
    public List<AccountEntity> findAllAccNrs(Long customerId) {
        findCustomerById(customerId);

        return customerRepository.findAllById(customerId);
    }
}