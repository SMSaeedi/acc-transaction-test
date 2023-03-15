package at.co.account.repository;

import at.co.account.entity.AccountEntity;
import at.co.account.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByIdAndAccountId(Long customerId, Long accNr);

    List<AccountEntity> findAllById(Long customerId);
}