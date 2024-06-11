package com.example.demo.service;

import com.example.demo.dtos.request.CreateAccountRequest;
import com.example.demo.dtos.request.TransferRequest;
import com.example.demo.dtos.responses.AccountResponse;
import com.example.demo.exceptions.BankException;
import com.example.demo.model.Account;
import com.example.demo.model.AccountTransaction;
import com.example.demo.model.Customer;
import com.example.demo.model.enums.TransactionType;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private AccountTransactionService transactionService;


    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository, AccountTransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionService = transactionService;
    }

    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountResponse(account.getId(), account.getAccountNumber(), account.getBalance()))
                .collect(Collectors.toList());
    }



    public AccountResponse getAccountById(Long id){
        Account account=accountRepository.findById(id).orElseThrow(() -> new BankException(id+" " + " Numaralı Id'Ye Ait Hesap Bulunamadı", HttpStatus.NOT_FOUND));
        return new AccountResponse(account.getId(), account.getAccountNumber(), account.getBalance());

    }



    public AccountResponse createAccount(CreateAccountRequest request) {
        if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new BankException( request.getAccountNumber()+ " " + "Numaralı Hesap Zaten Kullanımda ",HttpStatus.CONFLICT);
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
            throw new BankException( request.getCustomerId() +" "+  "Müşteri Numaralı Kullanıcı Bulunamadı: " ,HttpStatus.NOT_FOUND);
        }
    }


    public void deleteAccount(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(accountId);
        } else {
            throw new BankException("Bu : " + " "+  accountId + " " + " Ye Ait Hesap Bulunamadı",HttpStatus.NOT_FOUND);
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
            throw new BankException("Bu : " + " "+  accountId + " " + " Ye Ait Hesap Bulunamadı",HttpStatus.NOT_FOUND);

        }
    }




    @Transactional
    public void transferBalance(TransferRequest transferRequest) {


        Optional<Account> fromAccountOptional = accountRepository.findById(transferRequest.getFromAccountId());
        Optional<Account> toAccountOptional = accountRepository.findById(transferRequest.getToAccountId());
       // eğer hesaplar yoksa istisna fırlatırız.
        if (!fromAccountOptional.isPresent()) {
            throw new BankException("Gönderen hesap bulunamadı: " + transferRequest.getFromAccountId(), HttpStatus.NOT_FOUND);
        }

        if (!toAccountOptional.isPresent()) {
            throw new BankException("Alıcı hesap bulunamadı: " + transferRequest.getToAccountId(), HttpStatus.NOT_FOUND);
        }

        Account fromAccount = fromAccountOptional.get();
        Account toAccount = toAccountOptional.get();

        if (fromAccount.getBalance() < transferRequest.getAmount()) {
            throw new BankException("Yetersiz bakiye", HttpStatus.BAD_REQUEST);
        }

        fromAccount.setBalance(fromAccount.getBalance() - transferRequest.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transferRequest.getAmount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);


        // Hesap hareketi kaydet
        AccountTransaction transactionFrom = new AccountTransaction();
        transactionFrom.setTransactionType(TransactionType.TRANSFER);
        transactionFrom.setAmount(transferRequest.getAmount());
        transactionFrom.setTransactionTime(LocalDateTime.now());
        transactionFrom.setAccount(fromAccount);
        transactionService.saveTransaction(transactionFrom);

        AccountTransaction transactionTo = new AccountTransaction();
        transactionTo.setTransactionType(TransactionType.TRANSFER);
        transactionTo.setAmount(transferRequest.getAmount());
        transactionTo.setTransactionTime(LocalDateTime.now());
        transactionTo.setAccount(toAccount);
        transactionService.saveTransaction(transactionTo);


    }



}
