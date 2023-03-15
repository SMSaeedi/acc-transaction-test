package at.co.account.dto;

import at.co.account.enums.AccountType;
import at.co.account.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountUpdateDto {
    private Double initialCredit;
    private AccountType accountType;
    private TransactionType transactionType;
}