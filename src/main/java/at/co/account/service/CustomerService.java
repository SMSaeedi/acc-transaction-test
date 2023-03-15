package at.co.account.service;

import at.co.account.dto.GetCustomerTransaction;
import at.co.account.entity.AccountEntity;
import at.co.account.entity.CustomerEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CustomerService {
    GetCustomerTransaction getCustomerTransaction(Long customerId);
    CustomerEntity findCustomerById(Long customerId);

    CustomerEntity findOneAccNr(Long customerId, Long accNr);

    List<AccountEntity> findAllAccNrs(Long customerId);
}