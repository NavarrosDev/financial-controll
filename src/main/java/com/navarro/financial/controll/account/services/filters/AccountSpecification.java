package com.navarro.financial.controll.account.services.filters;

import com.navarro.financial.controll.account.entities.Account;
import com.navarro.financial.controll.account.services.filters.dto.AccountFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AccountSpecification {

    public static Specification<Account> filter(AccountFilter filter, UUID userId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("user").get("userId"), userId));

            if (Objects.nonNull(filter.active())) {
                predicates.add(cb.equal(root.get("active"), filter.active()));
            }

            if (Objects.nonNull(filter.type()) && !filter.type().isEmpty()) {
                predicates.add(cb.equal(root.get("type"), filter.type()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Account> byId(Long id) {
        return (root, query, cb) -> cb.equal(root.get("accountId"), id);
    }
}

