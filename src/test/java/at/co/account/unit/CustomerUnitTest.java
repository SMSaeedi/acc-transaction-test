package at.co.account.unit;

import at.co.account.exception.NotFoundException;
import at.co.account.service.CustomerService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerUnitTest extends BaseUnitTest {
    @Autowired
    private CustomerService customerService;

    @Test
    @Order(1)
    void should_get_customer_transactions() {
        //todo: accountEntity service must be mocked and then added to customerEntity then call this method
        /*var customer = customerService.findCustomerById(1L);
        var entity = customerService.getCustomerTransaction(customer.getId());

        assertEquals(2, entity.getTransaction().size());
        assertEquals("Mahsa", entity.getCustomerName());
        assertEquals("Saeedi", entity.getCustomerSirName());*/
    }

    @Test
    @Order(2)
    public void should_get_one_customer_by_id() {
        var customer = customerService.findCustomerById(1L);
        var entity = customerService.findCustomerById(customer.getId());

        assertEquals("Mahsa", entity.getName());
        assertEquals("Saeedi", entity.getSirName());
    }

    @Test
    @Order(3)
    public void get_one_customer_by_wrong_id_throw_not_found_exception() {
        assertThrows(NotFoundException.class,
                () -> customerService.findCustomerById(5L));
        assertEquals("NOT_FOUND", HttpStatus.NOT_FOUND.name());
        assertEquals(404, HttpStatus.NOT_FOUND.value());
    }
}