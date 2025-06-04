package com.navarro.financial.controll.account.services.account;

import com.navarro.financial.controll.account.dto.account.AccountRequest;
import com.navarro.financial.controll.account.dto.account.AccountRequestUpdate;
import com.navarro.financial.controll.account.dto.account.AccountResponse;
import com.navarro.financial.controll.account.services.account.filters.dto.AccountFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface AccountService {
    AccountResponse getAccountById(Long id, JwtAuthenticationToken token, AccountFilter filter);
    Page<AccountResponse> getAccounts(JwtAuthenticationToken token, AccountFilter filter, Pageable pageable);
    AccountResponse createAccount(AccountRequest request, JwtAuthenticationToken token);
    AccountResponse updateAccount(Long id, AccountRequestUpdate request, JwtAuthenticationToken token);
    Void deleteAccount(Long id);
}
