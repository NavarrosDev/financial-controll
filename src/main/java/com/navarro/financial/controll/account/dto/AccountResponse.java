package com.navarro.financial.controll.account.dto;

import com.navarro.financial.controll.account.entities.Account;
import com.navarro.financial.controll.account.entities.enums.Currency;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountResponse(Long id,
                              String name,
                              BigDecimal balance,
                              Currency currency,
                              boolean active,
                              Instant createdAt,
                              Instant updateAt,
                              String accountTypeName,
                              String username
                              ) {

    public AccountResponse(Account account) {
        this(   account.getAccountId(),
                account.getName(),
                account.getBalance(),
                account.getCurrency(),
                account.isActive(),
                account.getCreatedAt(),
                account.getUpdateAt(),
                account.getAccountType().getName(),
                account.getUser().getUsername()
                );
    }
}