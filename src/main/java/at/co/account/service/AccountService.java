package at.co.account.service;

import at.co.account.dto.AccountDto;
import at.co.account.dto.AccountUpdateDto;
import at.co.account.entity.AccountEntity;
import at.co.account.enums.AccountType;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface AccountService {
    AccountEntity createAccount(Long customerId, AccountDto accountDto);
    AccountEntity updateAccount(Long customerId, AccountUpdateDto accountDto);
    AccountEntity findAccountByNr(Long accNr);
    Optional<AccountEntity> hasAccount(AccountType accType);
    Double getLastBalance(Long customerId, Long accNr, AccountType accountType);
}