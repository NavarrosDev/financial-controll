package com.navarro.financial.controll.account.services;

import com.navarro.financial.controll.account.dto.AccountRequest;
import com.navarro.financial.controll.account.dto.AccountRequestUpdate;
import com.navarro.financial.controll.account.dto.AccountResponse;
import com.navarro.financial.controll.account.entities.Account;
import com.navarro.financial.controll.account.exceptions.AccountAlreadyExists;
import com.navarro.financial.controll.account.exceptions.AccountNotFoundException;
import com.navarro.financial.controll.account.respositories.AccountRepository;
import com.navarro.financial.controll.account.respositories.AccountTypeRepository;
import com.navarro.financial.controll.account.services.filters.AccountSpecification;
import com.navarro.financial.controll.account.services.filters.dto.AccountFilter;
import com.navarro.financial.controll.authentication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    public AccountResponse getAccountById(Long id, JwtAuthenticationToken token, AccountFilter filter) {
        UUID userId = UUID.fromString(token.getName());
        Specification<Account> spec = Specification
                .where(AccountSpecification.byId(id))
                .and(AccountSpecification.filter(filter, userId));

        return this.accountRepository.findOne(spec)
                .map(AccountResponse::new)
                .orElseThrow(() -> new AccountNotFoundException("Account not found!"));
    }

    @Override
    public Page<AccountResponse> getAccounts(JwtAuthenticationToken token, AccountFilter filter, Pageable pageable) {
        UUID userId = UUID.fromString(token.getName());
        Specification<Account> spec = AccountSpecification.filter(filter, userId);

        return this.accountRepository.findAll(spec, pageable)
                .map(AccountResponse::new);
    }

    @Override
    public AccountResponse createAccount(AccountRequest request, JwtAuthenticationToken token) {
        this.accountRepository.findByNameAndActiveTrue(request.name())
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
    @Transactional
    public AccountResponse updateAccount(Long id, AccountRequestUpdate request, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());

        return this.accountRepository.findByAccountIdAndUser_UserIdAndActiveTrue(id, userId)
                .map(account -> {
                    account.setBalance(request.balance());
                    account.setCurrency(request.currency());
                    account.setUpdateAt(Instant.now());
                    return new AccountResponse(account);
                })
                .orElseThrow(() -> new AccountNotFoundException("Account not found!"));
    }

    @Override
    @Transactional
    public Void deleteAccount(Long id) {
        this.accountRepository.findById(id).filter(Account::isActive)
                .ifPresentOrElse(
                        account -> account.setActive(false),
                        () -> { throw new AccountNotFoundException("Account not found!"); });
        return null;
    }
}
