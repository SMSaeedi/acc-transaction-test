package at.co.account.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false, columnDefinition = "char(15)", length = 15)
    private String createdBy = "";
    @CreatedDate
    @Column(name = "CREATED_ON", updatable = false)
    private LocalDate createdOn;
    @LastModifiedBy
    @Column(name = "MODIFIED_BY", columnDefinition = "char(15)", length = 15)
    private String modifiedBy = "";
    @LastModifiedDate
    @Column(name = "MODIFIED_ON")
    private LocalDate modifiedOn;
}
