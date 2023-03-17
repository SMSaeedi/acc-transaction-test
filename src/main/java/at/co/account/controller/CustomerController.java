package at.co.account.controller;

import at.co.account.dto.CustomerInfoDto;
import at.co.account.dto.GetCustomerTransaction;
import at.co.account.dto.mapper.CustomerMapper;
import at.co.account.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(description = "Customer service management", name = "Customer")
@RequestMapping("${v1API}/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/{customerId}")
    @Operation(summary = "Get Customer's transactions")
    @ResponseStatus(HttpStatus.OK)
    public GetCustomerTransaction getCustomerTransaction(@PathVariable Long customerId) {
        var getTransactions = customerService.getCustomerTransaction(customerId);

        return getTransactions;
    }

    @GetMapping("/find-one/{customerId}")
    @Operation(summary = "Get one customer's info")
    @ResponseStatus(HttpStatus.OK)
    public CustomerInfoDto findOne(@PathVariable Long customerId) {
        var customer = customerService.findCustomerById(customerId);

        return customerMapper.toDto(customer);
    }
}