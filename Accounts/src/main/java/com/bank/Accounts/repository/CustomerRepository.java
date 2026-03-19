package com.bank.Accounts.repository;

import com.bank.Accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * find by mobile number , fetch the records from the mobile number
     */
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
