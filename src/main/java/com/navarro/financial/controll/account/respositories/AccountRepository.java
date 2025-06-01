package com.navarro.financial.controll.account.respositories;

import com.navarro.financial.controll.account.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByName(String name);
    List<Account> findByUser_UserIdAndActiveTrue(UUID userId);
    Optional<Account> findByAccountIdAndUser_UserIdAndActiveTrue(Long accountId, UUID userId);

}
