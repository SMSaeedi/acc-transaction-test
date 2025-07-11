package at.co.account.entity;

import at.co.account.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long id;
    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private Long accNr;
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;
    @Column(name = "BALANCE", nullable = false)
    private Double balance;
    @Column(name = "ACCOUNT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}