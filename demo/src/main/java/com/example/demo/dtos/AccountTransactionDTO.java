package com.example.demo.dtos;
import com.example.demo.dtos.request.TransferRequest;
import com.example.demo.model.AccountTransaction;
import com.example.demo.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionDTO {
    private Long id;
    private TransactionType transactionType;
    private double amount;
    private LocalDateTime transactionTime;
    private Long accountId;
    private Long fromAccountId;
    private Long toAccountId;



    public static AccountTransactionDTO convertToDTO(AccountTransaction transaction, TransferRequest transferRequest) {
        AccountTransactionDTO transactionDTO = new AccountTransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setTransactionTime(transaction.getTransactionTime());
        transactionDTO.setAccountId(transaction.getAccount().getId());
        transactionDTO.setFromAccountId(transferRequest.getFromAccountId());
        transactionDTO.setToAccountId(transferRequest.getToAccountId());


        return transactionDTO;

    }
}