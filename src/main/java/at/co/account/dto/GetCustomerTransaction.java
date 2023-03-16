package at.co.account.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCustomerTransaction {
    private String customerName;
    private String customerSirName;
    private Double totalBalance;
    private List<TransactionDto> transaction;
}