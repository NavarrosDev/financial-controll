package com.navarro.financial.controll.account.controller;

import com.navarro.financial.controll.account.dto.accountType.AccountTypeRequest;
import com.navarro.financial.controll.account.dto.accountType.AccountTypeResponse;
import com.navarro.financial.controll.account.services.accountType.AccountTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accountType")
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    public AccountTypeController(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountTypeResponse>> getAllAccountTypes() {
        return ResponseEntity.ok(this.accountTypeService.getAllAccountTypes());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountTypeResponse> getAllAccountTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(this.accountTypeService.getAccountTypeById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountTypeResponse> createAccountType(@RequestBody AccountTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.accountTypeService.createAccountType(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountTypeResponse> updateAccountType(@PathVariable Long id,
                                                                 @RequestBody AccountTypeRequest request) {
        return ResponseEntity.ok(this.accountTypeService.updateAccountType(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAccountTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(this.accountTypeService.deleteAccountType(id));
    }
}
