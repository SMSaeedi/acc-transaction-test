package at.co.account.dto;

import at.co.account.enums.AccountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private Double amount;
    private AccountType accountType;
}