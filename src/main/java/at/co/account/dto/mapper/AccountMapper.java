package at.co.account.dto.mapper;

import at.co.account.dto.AccountDto;
import at.co.account.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    AccountDto toDto(AccountEntity entities);
}