package com.example.demo.dtos.responses;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long accountId;
    private String accountNumber;
    private double balance;
}
