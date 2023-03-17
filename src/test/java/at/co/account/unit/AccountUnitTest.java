package at.co.account.unit;

import at.co.account.dto.AccountDto;
import at.co.account.dto.AccountUpdateDto;
import at.co.account.enums.AccountType;
import at.co.account.enums.TransactionType;
import at.co.account.service.AccountService;
import at.co.account.service.CustomerService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountUnitTest extends BaseUnitTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;

    private AccountDto accDto() {
        return AccountDto.builder()
                .amount(2500D)
                .accountType(AccountType.CURRENT)
                .build();
    }

    private AccountUpdateDto accUpdateDto() {
        return AccountUpdateDto.builder()
                .amount(2600D)
                .accountType(AccountType.SAVING)
                .transactionType(TransactionType.CREDIT)
                .build();
    }

    @Test
    @Order(1)
    void should_create_account() {
        var customer = customerService.findCustomerById(1L);
        var entity = accountService.createAccount(customer.getId(), accDto());

        assertEquals(AccountType.CURRENT, entity.getAccountType());
        assertEquals(2500D, entity.getAmount());
    }

    @Test
    @Order(2)
    public void should_update_account() {
        var customer = customerService.findCustomerById(1L);
        var entity = accountService.updateAccount(customer.getId(), accUpdateDto());

        assertEquals(AccountType.SAVING, entity.getAccountType());
        assertEquals(2600D, entity.getAmount());
    }
}