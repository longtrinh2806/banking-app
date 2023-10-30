package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.model.Account;
import com.trinhkimlong.banking.model.User;
import com.trinhkimlong.banking.repository.AccountRepository;
import com.trinhkimlong.banking.repository.PaymentRepository;
import com.trinhkimlong.banking.request.PaymentRequest;
import com.trinhkimlong.banking.response.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final AccountRepository accountRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserService userService, AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.accountRepository = accountRepository;
    }

    @Override
    public PaymentResponse transfer(String token, PaymentRequest request) throws UserNotFoundException {
        User tmp = userService.findUserProfileByJwt(token);
        Account beneficiaryAccount = accountRepository.findByAccountNumber(request.getBeneficiaryAccount());

        if (tmp != null && beneficiaryAccount!= null &&
                !beneficiaryAccount.getAccountNumber().equals(request.getAccountNumber())) {

            List<Account> accountList = accountRepository.findAccountsByUserId(tmp.getUserId());
            for (Account account : accountList) {
                if (account.getAccountNumber().equals(request.getAccountNumber()) && account.getBalance() >= request.getAmount()) {

                    account.setBalance(account.getBalance() - request.getAmount());
                    beneficiaryAccount.setBalance(beneficiaryAccount.getBalance() + request.getAmount());
                    accountRepository.save(account);
                    accountRepository.save(beneficiaryAccount);
                }
            }
            return PaymentResponse
                    .builder()
                    .message("Transfer Successfully")
                    .build();
        }
        else throw new UserNotFoundException("Invalid amount or beneficiary number!!!");
    }
}
