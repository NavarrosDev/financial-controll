package com.navarro.financial.controll.respositories;

import com.navarro.financial.controll.entities.AccountType;
import com.navarro.financial.controll.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
