package at.co.account.repository;

import at.co.account.entity.AccountEntity;
import at.co.account.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long>{
    Optional<AccountEntity> findByAccountType(AccountType accType);
    Optional<AccountEntity> findByAccNr(Long accNr);
}