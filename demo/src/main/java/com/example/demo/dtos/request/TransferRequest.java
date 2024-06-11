package com.example.demo.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

    @NotNull(message = "Gönderen Hesap ID'si Boş Olamaz")
    private Long fromAccountId;

    @NotNull(message = "Alıcı Hesap ID'si Boş Olamaz")
    private Long toAccountId;

    @Min(value = 0, message = "Transfer Miktarı Negatif Olamaz")
    private double amount;
}