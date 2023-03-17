package at.co.account.integration;

import at.co.account.dto.AccountDto;
import at.co.account.dto.AccountUpdateDto;
import at.co.account.entity.AccountEntity;
import at.co.account.entity.CustomerEntity;
import at.co.account.enums.AccountType;
import at.co.account.enums.TransactionType;
import at.co.account.service.CustomerService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public class AccountControllerTest extends BaseIntegrationTest {
    @Autowired
    private CustomerService customerService;

    private AccountEntity accModel() {
        return AccountEntity.builder()
                .id(1L)
                .accNr(1234568L)
                .amount(2500D)
                .accountType(AccountType.CURRENT)
                .balance(2500D)
                .build();
    }

    private AccountDto accDto() {
        return AccountDto.builder()
                .amount(2500D)
                .accountType(AccountType.CURRENT)
                .build();
    }

    private AccountUpdateDto accUpdateDto() {
        return AccountUpdateDto.builder()
                .amount(2500D)
                .accountType(AccountType.CURRENT)
                .transactionType(TransactionType.CREDIT)
                .build();
    }

    private CustomerEntity customerModel() {
        return CustomerEntity.builder()
                .id(1L)
                .name("John")
                .sirName("Doe")
                .accountId(accModel().getId())
                .accountEntity(accModel())
                .build();
    }

    @Test
    @Order(1)
    void should_create_account() {
        CustomerEntity customerById = customerService.findCustomerById(1L);
        webTestClient.post().uri(CONTEXT_PATH + "/accounts/" + customerById.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(accDto()), AccountDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountDto.class);
    }

    @Test
    @Order(2)
    public void should_update_account() {
        CustomerEntity customerById = customerService.findCustomerById(1L);
        webTestClient.put().uri(CONTEXT_PATH + "/accounts/update/" + customerById.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(accUpdateDto()), AccountUpdateDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountDto.class);
    }
}