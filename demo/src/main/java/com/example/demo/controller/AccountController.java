package com.example.demo.controller;
import com.example.demo.dtos.request.CreateAccountRequest;
import com.example.demo.dtos.responses.AccountResponse;
import com.example.demo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getCustomerById(  @PathVariable Long id) {
        AccountResponse customer = accountService.getAccountById(id);
        return ResponseEntity.ok(customer);
    }


    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        AccountResponse createdAccount = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }


    @PutMapping("/update/{accountId}")
    public ResponseEntity<AccountResponse> updateAccount(
          @Valid  @PathVariable Long accountId,
            @RequestBody CreateAccountRequest request) {
        AccountResponse updatedAccount = accountService.updateAccount(accountId, request);
        return ResponseEntity.ok(updatedAccount);
    }


    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok("Hesap Başarıyla Silindi.");
    }
}
