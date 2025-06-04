package com.navarro.financial.controll.account.controller;

import com.navarro.financial.controll.account.dto.account.AccountRequest;
import com.navarro.financial.controll.account.dto.account.AccountRequestUpdate;
import com.navarro.financial.controll.account.dto.account.AccountResponse;
import com.navarro.financial.controll.account.services.account.AccountService;
import com.navarro.financial.controll.account.services.account.filters.dto.AccountFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id,
                                                          JwtAuthenticationToken token,
                                                          @ModelAttribute AccountFilter filter) {
        return ResponseEntity.ok(this.accountService.getAccountById(id, token, filter));
    }

    @GetMapping
    public Page<AccountResponse> getAllAccount(JwtAuthenticationToken token,
                                               @ModelAttribute AccountFilter filter,
                                               Pageable pageable) {
        return this.accountService.getAccounts(token, filter, pageable);
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
