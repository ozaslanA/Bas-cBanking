package com.example.demo.dtos;

import com.example.demo.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private double balance;


    public static AccountDTO fromEntity(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance()
        );
    }
}