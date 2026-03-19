package com.bank.Accounts.service.impl;

import com.bank.Accounts.constants.AccountsConstans;
import com.bank.Accounts.dto.AccountsDto;
import com.bank.Accounts.entity.Accounts;
import com.bank.Accounts.entity.Customer;
import com.bank.Accounts.exception.CustomerAlreadyExistException;
import com.bank.Accounts.exception.ResourceNotFoundException;
import com.bank.Accounts.mapper.AccountsMapper;
import com.bank.Accounts.mapper.CustomerMapper;
import com.bank.Accounts.service.IAccountService;
import com.bank.Accounts.dto.CustomerDto;
import com.bank.Accounts.repository.AccountRepository;
import com.bank.Accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service  // acts as service layer and IOC container will handle of this class bean
@AllArgsConstructor //from Lombok-  to create a all parameter constructor so that constructor injection will happen
public class AccountServiceImpl implements IAccountService {


    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    /**
     * To create a new customer and add in account db
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        // map DTO obj to customer obj through mapper
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> byMobileNumber = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (byMobileNumber.isPresent()){
            throw new CustomerAlreadyExistException("Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
        }

        /**
         * data jpa is doing automatically
         * taking the input, making the SQL query, creating the connection,executing the query
         * ,commiting the transaction , closing the connection
         * .save() method from jpa to store in DB
         */
        Customer savedCustomer= customerRepository.save(customer); // save in DB
        // now we need to create bank account
        accountRepository.save(createNewAccount(savedCustomer));
    }


    /**
     * method is responsible to take customer input and fetch details of customer and add in accounts details
     * @param customer
     * @return Accounts
     */
    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        Long randomAccountNumber = 1000000000L + new Random().nextInt((int) 9000000000L);
        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setAccountType(AccountsConstans.SAVINGS);
        newAccount.setBranchAddress(AccountsConstans.ADDRESS);

        return newAccount;
    }

    /**
     * fetch the account by mobile number
     * @param mobileNumber
     * @return CustomerDto
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

       Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer" ,"mobileNumber",mobileNumber)
        );
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountDto(accounts,new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated= false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null){
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated= true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
