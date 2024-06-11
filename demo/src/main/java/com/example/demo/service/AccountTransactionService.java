package com.example.demo.service;
import com.example.demo.dtos.AccountTransactionDTO;
import com.example.demo.dtos.request.TransferRequest;
import com.example.demo.model.AccountTransaction;
import com.example.demo.repository.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountTransactionService {

    @Autowired
    private AccountTransactionRepository transactionRepository;

    public AccountTransactionService(AccountTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void saveTransaction(AccountTransaction transaction) {
        transactionRepository.save(transaction);

    }

    @Transactional
    public List<AccountTransactionDTO> getAllTransactions() {
        List<AccountTransaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transaction -> AccountTransactionDTO.convertToDTO(transaction, new TransferRequest()))
                .collect(Collectors.toList());
    }


    @Transactional
    public List<AccountTransactionDTO> getTransactionsByAccountId(Long accountId) {
        List<AccountTransaction> transactions = transactionRepository.findByAccountId(accountId);
        return transactions.stream()
                .map(transaction -> AccountTransactionDTO.convertToDTO(transaction, new TransferRequest()))
                .collect(Collectors.toList());
    }
}