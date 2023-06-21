package com.example.demo.dto;

import com.example.demo.domain.Transaction;
import com.example.demo.type.TransactionResultType;
import com.example.demo.type.TransactionType;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private  String accountNumber;
    private TransactionType transactionType;
    private TransactionResultType transactionResultType;
    private Long amount;
    private Long balanceSnapshot;
    private String transactionId;
    private LocalDateTime transactedAt;

    public static TransactionDto fromEntity(Transaction transaction){
        return TransactionDto.builder()
                .accountNumber(transaction.getAccount().getAccountNumber())
                .transactionType(transaction.getTransactionType())
                .transactionResultType(transaction.getTransactionResultType())
                .amount(transaction.getAmount())
                .balanceSnapshot(transaction.getBalanceSnapshot())
                .transactionId(transaction.getTransactionId())
                .transactedAt(transaction.getTransactedAt())
                .build();
    }
}
