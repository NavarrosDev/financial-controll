package com.navarro.financial.controll.account.respositories;

import com.navarro.financial.controll.account.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByNameAndActiveTrue(String name);
    Optional<Account> findByAccountIdAndUser_UserIdAndActiveTrue(Long accountId, UUID userId);
}
