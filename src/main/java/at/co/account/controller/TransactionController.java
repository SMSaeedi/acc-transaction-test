package at.co.account.controller;

import at.co.account.entity.TransactionEntity;
import at.co.account.service.CreditService;
import at.co.account.service.DebitService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(description = "Debit and Credit service management", name = "Transactions")
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Hidden
public class TransactionController {
    private final CreditService creditService;
    private final DebitService debitService;

    @PostMapping("/credit/{customerId}/{accNr}/{amount}")
    @Operation(summary = "Credit account")
    @ResponseStatus(HttpStatus.OK)
    public TransactionEntity credit(@PathVariable Long customerId, @PathVariable Long accNr, @PathVariable Double amount) {
        var credit = creditService.credit(customerId, accNr, amount);

        return credit;
    }

    @PostMapping("/debit/{customerId}/{accNr}/{amount}")
    @Operation(summary = "Debit account")
    @ResponseStatus(HttpStatus.OK)
    public TransactionEntity debit(@PathVariable Long customerId, @PathVariable Long accNr, @PathVariable Double amount) {
        var debit = debitService.debit(customerId, accNr, amount);

        return debit;
    }
}