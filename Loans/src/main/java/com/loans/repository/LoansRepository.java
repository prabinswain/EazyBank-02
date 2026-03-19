package com.loans.repository;

import com.loans.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Long> {



   public Optional<Loans>  findByMobileNumber(String mobileNumber);


}
