package at.co.account.dto.mapper;

import at.co.account.dto.CustomerInfoDto;
import at.co.account.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    CustomerInfoDto toDto(CustomerEntity entities);
}