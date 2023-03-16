package at.co.account.service.impl;

import at.co.account.dto.CustomerDto;
import at.co.account.dto.GetCustomerTransaction;
import at.co.account.dto.mapper.TransactionMapper;
import at.co.account.entity.AccountEntity;
import at.co.account.entity.CustomerEntity;
import at.co.account.exception.Errors;
import at.co.account.exception.NotFoundException;
import at.co.account.repository.AccountRepository;
import at.co.account.repository.CustomerRepository;
import at.co.account.service.CustomerService;
import at.co.account.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    @Override
    public GetCustomerTransaction getCustomerTransaction(Long customerId) {
        var customerEntity = findCustomerById(customerId);
        var customerDto = objectMapper.convertValue(customerEntity, CustomerDto.class);
        var accountEntity = accountRepository.findByAccNr(customerEntity.getAccountId());

        var transactionEntities = transactionService.allCustomersTransactions(customerEntity.getId());
        var transactionDtos = transactionMapper.toListDto(transactionEntities);

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