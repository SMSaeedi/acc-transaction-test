package at.co.account.controller;

import at.co.account.dto.AccountDto;
import at.co.account.dto.AccountUpdateDto;
import at.co.account.dto.mapper.AccountMapper;
import at.co.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(description = "Account service management", name = "Account")
@RequestMapping("${v1API}/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/{customerId}")
    @Operation(summary = "Create account for a specific customer")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@PathVariable Long customerId, @RequestBody AccountDto accountDto) {
        var createAcc = accountService.createAccount(customerId, accountDto);

        return accountMapper.toDto(createAcc);
    }

    @PutMapping("/update/{customerId}")
    @Operation(summary = "Update account for a specific customer")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto updateAccount(@PathVariable Long customerId, @RequestBody AccountUpdateDto accountDto) {
        var updateAcc = accountService.updateAccount(customerId, accountDto);

        return accountMapper.toDto(updateAcc);
    }
}