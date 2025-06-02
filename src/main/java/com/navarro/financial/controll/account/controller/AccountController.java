package com.navarro.financial.controll.account.controller;

import com.navarro.financial.controll.account.dto.AccountRequest;
import com.navarro.financial.controll.account.dto.AccountRequestUpdate;
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
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id,
                                                          JwtAuthenticationToken token) {
        return ResponseEntity.ok(this.accountService.getAccountById(id, token));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccount(JwtAuthenticationToken token) {
        return ResponseEntity.ok(this.accountService.getAccounts(token));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request,
                                                         JwtAuthenticationToken token) {
        return ResponseEntity.ok(this.accountService.createAccount(request, token));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long id,
                                                         @RequestBody AccountRequestUpdate request,
                                                         JwtAuthenticationToken token) {
        return ResponseEntity.ok(this.accountService.updateAccount(id, request, token));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id,
                                              JwtAuthenticationToken token) {
        return ResponseEntity.ok(this.accountService.deleteAccount(id));
    }
}
