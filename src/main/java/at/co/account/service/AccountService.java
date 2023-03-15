package at.co.account.service;

import at.co.account.dto.AccountDto;
import at.co.account.entity.AccountEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AccountService {
    AccountEntity createAccount(Long customerId, AccountDto accountDto);
    AccountEntity findAccountByNr(Long accNr);
    Double getLastBalance(Long customerId, Long accNr, int accountType);
}