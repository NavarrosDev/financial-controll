package com.navarro.financial.controll.account.services;

import com.navarro.financial.controll.account.dto.AccountRequest;
import com.navarro.financial.controll.account.dto.AccountRequestUpdate;
import com.navarro.financial.controll.account.dto.AccountResponse;
import com.navarro.financial.controll.account.services.filters.dto.AccountFilter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public interface AccountService {
    AccountResponse getAccountById(Long id, JwtAuthenticationToken token, AccountFilter filter);
    List<AccountResponse> getAccounts(JwtAuthenticationToken token, AccountFilter filter);
    AccountResponse createAccount(AccountRequest request, JwtAuthenticationToken token);
    AccountResponse updateAccount(Long id, AccountRequestUpdate request, JwtAuthenticationToken token);
    Void deleteAccount(Long id);
}
