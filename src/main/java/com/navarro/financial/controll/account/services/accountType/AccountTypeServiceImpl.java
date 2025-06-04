package com.navarro.financial.controll.account.services.accountType;

import com.navarro.financial.controll.account.dto.accountType.AccountTypeRequest;
import com.navarro.financial.controll.account.dto.accountType.AccountTypeResponse;
import com.navarro.financial.controll.account.entities.AccountType;
import com.navarro.financial.controll.account.respositories.AccountTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTypeServiceImpl implements AccountTypeService{

    private final AccountTypeRepository accountTypeRepository;

    public AccountTypeServiceImpl(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override
    public List<AccountTypeResponse> getAllAccountTypes() {
        return this.accountTypeRepository.findAll().stream().map(AccountTypeResponse::new).toList();
    }

    @Override
    public AccountTypeResponse getAccountTypeById(Long id) {
        return this.accountTypeRepository.findById(id).map(AccountTypeResponse::new)
                .orElseThrow(() -> new RuntimeException()); // Tratar
    }

    @Override
    public AccountTypeResponse createAccountType(AccountTypeRequest request) {
        this.accountTypeRepository.findByName(request.name())
                .ifPresent((accountType -> { throw new RuntimeException(); })); // Tratar

        AccountType accountType = new AccountType();
        accountType.setName(request.name());

        this.accountTypeRepository.save(accountType);
        return new AccountTypeResponse(accountType);
    }

    @Override
    @Transactional
    public AccountTypeResponse updateAccountType(Long id, AccountTypeRequest request) {
        return this.accountTypeRepository.findById(id)
                .map(accountType -> {
                    accountType.setName(request.name());
                    return new AccountTypeResponse(accountType);
                }).orElseThrow(() -> new RuntimeException()); // Tratar
    }

    @Override
    public Void deleteAccountType(Long id) {
        this.accountTypeRepository.findById(id)
                .ifPresentOrElse(
                        this.accountTypeRepository::delete,
                        () -> { throw new RuntimeException(); } ); // Tratar
        return null;
    }
}
