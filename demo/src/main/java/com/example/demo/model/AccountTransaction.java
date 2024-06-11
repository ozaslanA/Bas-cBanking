package com.example.demo.model;

import com.example.demo.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ACCOUNT_TRANSACTİONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRANSTACTION_TYPE")
    private TransactionType transactionType;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "TRANSTACTİON_TIME")
    private LocalDateTime transactionTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;
}
