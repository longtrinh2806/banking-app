package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.AccountException;
import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.model.Account;
import com.trinhkimlong.banking.model.Payment;
import com.trinhkimlong.banking.model.TransactionType;
import com.trinhkimlong.banking.model.User;
import com.trinhkimlong.banking.repository.AccountRepository;
import com.trinhkimlong.banking.repository.PaymentRepository;
import com.trinhkimlong.banking.request.WithdrawRequest;
import com.trinhkimlong.banking.response.WithdrawResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawServiceImpl implements WithdrawService {
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    public WithdrawServiceImpl(UserService userService, AccountRepository accountRepository, PaymentRepository paymentRepository) {
        this.userService = userService;
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public WithdrawResponse withdrawMoney(String token, WithdrawRequest request) throws UserNotFoundException {
        User tmp = userService.findUserProfileByJwt(token);
        List<Account> accountList = accountRepository.findAccountsByUserId(tmp.getUserId());

        if (accountList != null) {
            for (Account account : accountList) {
                if (account.getAccountNumber().equals(request.getAccountNumber()) && account.getBalance() >= request.getAmount()) {
                    account.setBalance(account.getBalance() - request.getAmount());
                    accountRepository.save(account);

                    Payment payment = new Payment();
                    payment.setPaymentId(0L);
                    payment.setAccount(account);
                    payment.setAmount(request.getAmount());
                    payment.setTransactionType(TransactionType.WITHDRAW);
                    paymentRepository.save(payment);

                    return WithdrawResponse.builder().message("Withdraw Successfully!!!").build();
                }
            }
            throw new AccountException("Insufficient balance or Invalid Account Number!!!");
        } else {
            throw new AccountException("Account Number Invalid!!!");
        }
    }


}
