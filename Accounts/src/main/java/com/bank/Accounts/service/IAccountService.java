package com.bank.Accounts.service;

import com.bank.Accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     * To cretae new account of customer
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);
    CustomerDto fetchAccount(String mobileNumber);
    boolean updateAccount(CustomerDto  customerDto);
    boolean deleteAccount( String mobileNumber);
}
