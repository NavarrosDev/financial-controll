package com.navarro.financial.controll.account.controller;

import com.navarro.financial.controll.account.dto.AccountRequest;
import com.navarro.financial.controll.account.dto.AccountResponse;
import com.navarro.financial.controll.account.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(this.accountService.getAccountById(id));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccount() {
        return ResponseEntity.ok(this.accountService.getAccounts());
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request,
                                                         JwtAuthenticationToken token) {
        return ResponseEntity.ok(this.accountService.createAccount(request, token));
    }
}
