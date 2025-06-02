package com.navarro.financial.controll.account.dto;

import com.navarro.financial.controll.account.entities.enums.Currency;

import java.math.BigDecimal;

public record AccountRequestUpdate(BigDecimal balance, Currency currency) {
}