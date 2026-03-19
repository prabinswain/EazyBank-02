package com.bank.Accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @ToString
@EntityListeners(AuditingEntityListener.class) // to enable auditing
public class BaseEntity {

//    Those @CreatedBy , @LastModifiedBy , @CreatedDate , @LastModifiedDate are tell auditing to spring data JPA

    @CreatedDate
    @Column( name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column( name = "created_by" , insertable = false)
    private String createdBy;

    @LastModifiedDate
    @Column( name = "updated_at", updatable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column( name = "updated_by" , insertable = false)
    private String updatedBy;
}
