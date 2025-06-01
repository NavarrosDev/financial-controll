package com.navarro.financial.controll.account.services;

import com.navarro.financial.controll.account.dto.AccountRequest;
import com.navarro.financial.controll.account.dto.AccountResponse;
import com.navarro.financial.controll.account.entities.Account;
import com.navarro.financial.controll.account.exceptions.AccountAlreadyExists;
import com.navarro.financial.controll.account.exceptions.AccountNotFoundException;
import com.navarro.financial.controll.account.respositories.AccountRepository;
import com.navarro.financial.controll.account.respositories.AccountTypeRepository;
import com.navarro.financial.controll.authentication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public AccountResponse getAccountById(Long id, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());

        Account account = accountRepository.findByAccountIdAndUser_UserIdAndActiveTrue(id, userId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found!"));

        return new AccountResponse(account);
    }


    @Override
    public List<AccountResponse> getAccounts(JwtAuthenticationToken token) {
        return accountRepository.findByUser_UserIdAndActiveTrue(UUID.fromString(token.getName()))
                .stream()
                .filter(Account::isActive)
                .map(AccountResponse::new)
                .toList();
    }

    @Override
    public AccountResponse createAccount(AccountRequest request, JwtAuthenticationToken token) {
        this.accountRepository.findByName(request.name())
                .ifPresent(account -> {
                    throw new AccountAlreadyExists(String.format("Account %s already exists", account.getName()));
                });

        Account account = new Account();
        account.setName(request.name());
        account.setBalance(request.balance());
        account.setCurrency(request.currency());
        this.userRepository.findById(UUID.fromString(token.getName())).ifPresent(account::setUser);
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
                .filter(Account::isActive)
                .ifPresentOrElse(account -> account.setActive(false),
                        () -> { throw new AccountNotFoundException("Account not found!"); });

        return null;
    }
}
