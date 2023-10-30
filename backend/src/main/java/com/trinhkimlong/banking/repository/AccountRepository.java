package com.trinhkimlong.banking.repository;

import com.trinhkimlong.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select a from Account a where a.user.userId = :userId")
    List<Account> findAccountsByUserId(@Param("userId") Long userId);

    @Query("select b.balance from Account b where b.user.userId = :userId")
    Long getTotalBalance(@Param("userId") Long userId);

    @Query("select a from Account a where a.accountNumber = :accountNumber")
    Account findByAccountNumber(@Param("accountNumber")String accountNumber);
}
