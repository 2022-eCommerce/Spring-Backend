package com.ecommerce.backend.repository;

import com.ecommerce.backend.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findById(long accountId);

    Optional<Account> findByEmailAndPasswordHash(String email, String passwordHash);
}
