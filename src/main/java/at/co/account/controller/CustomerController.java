package at.co.account.controller;

import at.co.account.dto.GetCustomerTransaction;
import at.co.account.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(description = "Customer service management", name = "Customer")
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    @Operation(summary = "Get Customer's transactions")
    @ResponseStatus(HttpStatus.OK)
    public GetCustomerTransaction getCustomerTransaction(@PathVariable Long customerId) {
        var getTransactions = customerService.getCustomerTransaction(customerId);

        return getTransactions;
    }
}