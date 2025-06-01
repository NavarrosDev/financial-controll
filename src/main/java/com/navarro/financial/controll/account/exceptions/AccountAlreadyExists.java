package com.navarro.financial.controll.account.exceptions;

public class AccountAlreadyExists extends RuntimeException{

    public AccountAlreadyExists() {
        super();
    }

    public AccountAlreadyExists(String message) {
        super(message);
    }
}
