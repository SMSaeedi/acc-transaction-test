//package at.co.account.dto.mapper;
//
//import at.co.account.dto.TransactionDto;
//import at.co.account.entity.TransactionEntity;
//import org.mapstruct.Mapper;
//import org.mapstruct.ReportingPolicy;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface TransactionMapper {
//    List<TransactionDto> toListDto(List<TransactionEntity> entities);
//}