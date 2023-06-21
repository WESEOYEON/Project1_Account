package com.example.demo.controller;

import com.example.demo.aop.AccountLock;
import com.example.demo.dto.CancelBalance;
import com.example.demo.dto.QueryTransactionResponse;
import com.example.demo.dto.UseBalance;
import com.example.demo.exception.AccountException;
import com.example.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 잔액 관련 컨트롤러
 * 1. 잔액사용
 * 2. 잔액사용취소
 * 3. 거래 확인
 */

@Slf4j
@RequiredArgsConstructor
@RestController
public class TransactionController {
    private final TransactionService transactionService;


    @PostMapping("/transaction/use")
    @AccountLock
    public UseBalance.Response useBalance(
            @Valid @RequestBody UseBalance.Request request
    ) throws InterruptedException {
        try {
            Thread.sleep(5000L);
            return UseBalance.Response.from(
                    transactionService.useBalance(request.getUserId(), request.getAccountNumber(), request.getAmount())
            );
        } catch (AccountException e) {
            log.error("Failed to use Balance");
            transactionService.saveFailedUseTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );

            throw e;
        }
    }

    @PostMapping("/transaction/cancel")
    @AccountLock
    public CancelBalance.Response cancelBalance(
            @Valid @RequestBody CancelBalance.Request request
    ) {
        try {
            return CancelBalance.Response.from(
                    transactionService.cancelBalance(
                            request.getTransactionId(),
                            request.getAccountNumber(),
                            request.getAmount())
            );
        } catch (AccountException e) {
            log.error("Failed to cancel Balance");
            transactionService.saveFailedCancelTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );

            throw e;
        }
    }


    @GetMapping("/transaction/{transactionId}")
    public QueryTransactionResponse queryTransaction(
            @PathVariable String transactionId) {
        return QueryTransactionResponse.from(
                transactionService.queryTransactions(transactionId)
        );
    }
}
