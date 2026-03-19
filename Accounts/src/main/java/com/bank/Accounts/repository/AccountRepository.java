package com.bank.Accounts.repository;

import com.bank.Accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts , Long> {
    Optional<Accounts> findByCustomerId(Long customerId);


    // @Modifying it tells Spring Data Jpa that the transaction will modify in DB and this transaction should complete if not rollback
    // any runtime error transaction will be rolled back
    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
