package at.co.account.dto;

import at.co.account.enums.AccountType;
import at.co.account.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDto {
    private Double amount;
    private AccountType accountType;
    private TransactionType transactionType;
}