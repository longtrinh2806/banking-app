package com.trinhkimlong.banking.repository;

import com.trinhkimlong.banking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(value = "SELECT p FROM Payment p WHERE p.account.accountId IN :accountIds")
    List<Payment> findByAccountIds(@Param("accountIds") List<Long> accountIds);
}
