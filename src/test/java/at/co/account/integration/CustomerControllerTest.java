package at.co.account.integration;

import at.co.account.dto.CustomerInfoDto;
import at.co.account.dto.GetCustomerTransaction;
import at.co.account.entity.CustomerEntity;
import at.co.account.service.CustomerService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerControllerTest extends BaseIntegrationTest {
    @Autowired
    private CustomerService customerService;

    @Test
    @Order(1)
    void should_get_customer_transactions() {
        CustomerEntity customerById = customerService.findCustomerById(1L);
        System.out.println("Customer: " + customerById.getId());
        webTestClient.get().uri(CONTEXT_PATH + "/customers/" + customerById.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetCustomerTransaction.class);
    }

    @Test
    @Order(2)
    public void should_get_one_customer_by_id() {
        CustomerEntity customerById = customerService.findCustomerById(1L);
        webTestClient.get().uri(CONTEXT_PATH + "/customers/find-one/" + customerById.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerInfoDto.class);
    }

    @Test
    @Order(3)
    public void get_one_customer_by_wrong_id_throw_not_found_exception() {
        webTestClient.get().uri(CONTEXT_PATH + "/customers/find-one/5")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(CustomerInfoDto.class);
    }
}