package com.example.demo.repository;

import com.example.demo.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTransactionRepository  extends JpaRepository<AccountTransaction,Long> {
    List<AccountTransaction> findByAccountId(Long accountId);

}
