package com.example.demo.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountResponse {
    private Long accountId;
    private String accountNumber;
    private double balance;
    private Long customerId;
    private String customerName;
    private String customerEmail;
}
