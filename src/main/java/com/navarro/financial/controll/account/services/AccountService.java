package com.navarro.financial.controll.account.services;

import com.navarro.financial.controll.account.dto.AccountRequest;
import com.navarro.financial.controll.account.dto.AccountResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public interface AccountService {
    AccountResponse getAccountById(Long id, JwtAuthenticationToken token);
    List<AccountResponse> getAccounts(JwtAuthenticationToken token);
    AccountResponse createAccount(AccountRequest request, JwtAuthenticationToken token);
    AccountResponse updateAccount(Long id, AccountRequest request);
    Void deleteAccount(Long id);
}
