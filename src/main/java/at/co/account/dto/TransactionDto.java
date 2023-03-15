package at.co.account.dto;

import at.co.account.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {
    private Double lastBalance;
    private Double initialCredit;
    private Long customerId;
    private CustomerDto customerDto;
    private TransactionType transactionType;
}