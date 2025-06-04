package com.navarro.financial.controll.account.dto.account;

import com.navarro.financial.controll.account.entities.enums.Currency;

import java.math.BigDecimal;

public record AccountRequest(String name, BigDecimal balance, Currency currency, Long accountType) {
}