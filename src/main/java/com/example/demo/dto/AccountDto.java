package com.example.demo.dto;

import com.example.demo.domain.Account;
import com.example.demo.exception.AccountException;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long userId;
    private String accountNumber;
    private Long balance;
    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;

    public static AccountDto fromEntity(Account account){
        return AccountDto.builder()
                .userId(account.getAccountUser().getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .registeredAt(account.getRegisteredAt())
                .unRegisteredAt(account.getUnRegisteredAt())
                .build();
    }

}
