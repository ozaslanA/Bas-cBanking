package com.example.demo.service;

import com.example.demo.dtos.request.CreateAccountRequest;
import com.example.demo.dtos.request.UpdateAccountRequest;
import com.example.demo.dtos.responses.AccountResponse;
import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }


    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountResponse(account.getId(), account.getAccountNumber(), account.getBalance()))
                .collect(Collectors.toList());
    }

    public AccountResponse getAccountById(Long id){
        Account account=accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        return new AccountResponse(account.getId(), account.getAccountNumber(), account.getBalance());

    }
    public AccountResponse createAccount(CreateAccountRequest request) {
        if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new RuntimeException("Bu Account number zaten kullanımda " + request.getAccountNumber());
        }
        Optional<Customer> optionalCustomer = customerRepository.findById(request.getCustomerId());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            Account account = new Account();
            account.setAccountNumber(request.getAccountNumber());
            account.setBalance(request.getInitialBalance());
            account.setCustomer(customer);

            account = accountRepository.save(account);
            return new AccountResponse(account.getId(), account.getAccountNumber(), account.getBalance());
        } else {
            throw new RuntimeException("Customer not found with id: " + request.getCustomerId());
        }
    }
    public void deleteAccount(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(accountId);
        } else {
            throw new RuntimeException("Account not found with id: " + accountId);
        }
    }
    public AccountResponse updateAccount(Long accountId, CreateAccountRequest request) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setAccountNumber(request.getAccountNumber());
            account.setBalance(request.getInitialBalance());

            account = accountRepository.save(account);

            return new AccountResponse(account.getId(), account.getAccountNumber(), account.getBalance());
        } else {
            throw new RuntimeException("Account not found with id: " + accountId);
        }
    }

}
