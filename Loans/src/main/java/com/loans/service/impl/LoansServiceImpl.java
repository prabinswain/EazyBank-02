package com.loans.service.impl;

import com.loans.constants.LoansConstants;
import com.loans.dto.LoansDto;
import com.loans.entity.Loans;
import com.loans.exception.LoanAlreadyExistsException;
import com.loans.exception.ResourceNotFoundException;
import com.loans.mapper.LoansMapper;
import com.loans.repository.LoansRepository;
import com.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;// constructor injection

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> loan = loansRepository.findByMobileNumber(mobileNumber); // check is the customer with account present or not by phone number
        if (loan.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " +mobileNumber);
        }
    loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
//        mobileNumber,loanNumber, loanType,totalLoan,amountPaid,outstandingAmount
        // mobile number already coming as argument
        Loans newLoan = new Loans();
        newLoan.setMobileNumber(mobileNumber);
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        //auditing fields

        return newLoan;

    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return return loan
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans,new LoansDto() );
    }


    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        boolean isUpdated= false;
        Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber())
        );
        loansRepository.save(LoansMapper.mapToLoans(loansDto, loans));
        return true;
    }

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
