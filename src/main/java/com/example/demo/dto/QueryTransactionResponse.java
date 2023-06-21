package com.example.demo.dto;

import com.example.demo.type.TransactionResultType;
import com.example.demo.type.TransactionType;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class QueryTransactionResponse {

    private String accountNumber;
    private TransactionType transactionType;
    private TransactionResultType transactionResult;
    private String transactionId;
    private Long amount;
    private LocalDateTime transactedAt;


    public static QueryTransactionResponse from(TransactionDto transactionDto) {
        return QueryTransactionResponse.builder()
                .accountNumber(transactionDto.getAccountNumber())
                .transactionType(transactionDto.getTransactionType())
                .transactionResult(transactionDto.getTransactionResultType())
                .transactionId(transactionDto.getTransactionId())
                .amount(transactionDto.getAmount())
                .transactedAt(transactionDto.getTransactedAt())
                .build();
    }
}
