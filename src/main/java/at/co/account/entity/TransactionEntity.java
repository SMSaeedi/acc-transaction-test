package at.co.account.entity;

import at.co.account.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TRANSACTION")
public class TransactionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long id;
    @Column(name = "OLD_BALANCE", nullable = false)
    private Double oldBalance;
    @Column(name = "NEW_BALANCE", nullable = false)
    private Double newBalance;
    @Column(name = "IS_TRANSACTION_SUCCEEDED")
    private boolean isTransactionSucceeded;
    @Column(name = "TRANSACTION_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(name = "CUSTOMER_FK", insertable = false, updatable = false)
    private Long customerId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CUSTOMER_FK", nullable = false)
    private CustomerEntity customerEntity;
}