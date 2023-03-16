package at.co.account.dto;

import at.co.account.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {
    private Double oldBalance;
    private Double newBalance;
    private TransactionType transactionType;
}
