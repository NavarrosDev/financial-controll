package com.navarro.financial.controll.account.services;

import com.navarro.financial.controll.account.dto.AccountRequest;
import com.navarro.financial.controll.account.dto.AccountResponse;
import com.navarro.financial.controll.account.entities.Account;
import com.navarro.financial.controll.account.respositories.AccountRepository;
import com.navarro.financial.controll.account.respositories.AccountTypeRepository;
import com.navarro.financial.controll.authentication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;

    public AccountServiceImpl(UserRepository userRepository,
                              AccountRepository accountRepository,
                              AccountTypeRepository accountTypeRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override
    public AccountResponse getAccountById(Long id) {
        return this.accountRepository.findById(id)
                .map(AccountResponse::new)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<AccountResponse> getAccounts() {
        return this.accountRepository.findAll().stream().map(AccountResponse::new).toList();
    }

    @Override
    public AccountResponse createAccount(AccountRequest request, JwtAuthenticationToken token) {
        this.accountRepository.findByName(request.name())
                .ifPresent(user -> {
                    throw new RuntimeException("Account already exists");
                });

        Account account = new Account();
        account.setName(request.name());
        account.setBalance(request.balance());
        account.setCurrency(request.currency());
        this.userRepository.findByUsername(token.getName()).ifPresent(account::setUser);
        this.accountTypeRepository.findById(request.accountType()).ifPresent(account::setAccountType);
        this.accountRepository.save(account);

        return new AccountResponse(account);
    }

    @Override
    public AccountResponse updateAccount(Long id, AccountRequest request) {
        return null;
    }

    @Override
    @Transactional
    public Void deleteAccount(Long id) {
        this.accountRepository.findById(id)
                .ifPresentOrElse(account -> account.setActive(false),
                        () -> {});

        return null;
    }
}
