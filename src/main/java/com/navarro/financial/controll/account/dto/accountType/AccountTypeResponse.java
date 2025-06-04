package com.navarro.financial.controll.account.dto.accountType;

import com.navarro.financial.controll.account.entities.AccountType;

public record AccountTypeResponse(Long id, String name) {

    public AccountTypeResponse(AccountType accountType) {
        this(accountType.getAccountTypeId(), accountType.getName());
    }
}
