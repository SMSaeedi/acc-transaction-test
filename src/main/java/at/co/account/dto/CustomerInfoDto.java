package at.co.account.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerInfoDto {
    private String name;
    private String sirName;
}