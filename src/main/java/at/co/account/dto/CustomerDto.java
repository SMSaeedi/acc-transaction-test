package at.co.account.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {
    private String name;
    private String sirNAme;
    private Long accountId;
    private AccountDto accountDto;
}