package com.example.demo.controller;
import com.example.demo.dtos.AccountTransactionDTO;
import com.example.demo.model.AccountTransaction;
import com.example.demo.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class AccountTransactionController {

    private final AccountTransactionService transactionService;

    @Autowired
    public AccountTransactionController(AccountTransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody AccountTransaction transaction) {
        transactionService.saveTransaction(transaction);
        return ResponseEntity.ok("İşlem Başarıyla Kaydedildi.");
    }


    @GetMapping("/all")
    public ResponseEntity<List<AccountTransactionDTO>> getAllTransactions() {
        List<AccountTransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }


    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<AccountTransactionDTO>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<AccountTransactionDTO> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

}