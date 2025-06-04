package com.navarro.financial.controll.account.services.accountType;

import com.navarro.financial.controll.account.dto.accountType.AccountTypeRequest;
import com.navarro.financial.controll.account.dto.accountType.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> getAllAccountTypes();
    AccountTypeResponse getAccountTypeById(Long id);
    AccountTypeResponse createAccountType(AccountTypeRequest request);
    AccountTypeResponse updateAccountType(Long id, AccountTypeRequest request);
    Void deleteAccountType(Long id);
}
