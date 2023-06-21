package com.example.demo.service;

import com.example.demo.exception.AccountException;
import com.example.demo.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Service
public class LockService {
    private final RedissonClient redissonClient;

    public void lock (String accountNumber){
        RLock lock = redissonClient.getLock(getLockKey(accountNumber));
        log.debug("Trying lock for acountNumber : {}" , accountNumber);

        try {
            boolean isLock = lock.tryLock(1, 15, TimeUnit.SECONDS);
            if (!isLock){
                log.error("=================Lock acquisition failed==================");
                throw new AccountException(ErrorCode.ACCOUNT_TRANSACTION_LOCK);
            }
        } catch(AccountException e) {
            throw e;
        }catch (Exception e){
            log.error("Redis lock failed", e);
        }
    }

    public void unLock (String accountNumber){
        log.debug("Unlock for accoutNumber : {}" , accountNumber);
        redissonClient.getLock(getLockKey(accountNumber)).unlock();
    }

    private static String getLockKey(String accountNumber) {
        return "ACLK:" + accountNumber;
    }
}