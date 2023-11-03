package com.trinhkimlong.banking.service.implementation;

import com.trinhkimlong.banking.exception.AccountException;
import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.model.Account;
import com.trinhkimlong.banking.model.Payment;
import com.trinhkimlong.banking.model.TransactionType;
import com.trinhkimlong.banking.model.User;
import com.trinhkimlong.banking.repository.AccountRepository;
import com.trinhkimlong.banking.repository.PaymentRepository;
import com.trinhkimlong.banking.request.DepositRequest;
import com.trinhkimlong.banking.response.DepositResponse;
import com.trinhkimlong.banking.service.DepositService;
import com.trinhkimlong.banking.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositServiceImpl implements DepositService {
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    public DepositServiceImpl(UserService userService, AccountRepository accountRepository, PaymentRepository paymentRepository) {
        this.userService = userService;
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public DepositResponse addMoney(String token, DepositRequest request) throws UserNotFoundException {
        User tmp = userService.findUserProfileByJwt(token);
        if (tmp != null) {
            List<Account> accountList = accountRepository.findAccountsByUserId(tmp.getUserId());

            boolean accountExisted = false;

            for (Account account : accountList) {
                if (account.getAccountNumber().equals(request.getAccountNumber())){
                    Long balance = account.getBalance() + request.getDepositAmount();
                    account.setBalance(balance);
                    accountExisted = true;

                    Payment payment = new Payment();
                    payment.setPaymentId(0L);
                    payment.setAccount(account);
                    payment.setAmount(request.getDepositAmount());
                    payment.setTransactionType(TransactionType.DEPOSIT);
                    paymentRepository.save(payment);
                }
            }
            if (accountExisted) {
                accountRepository.saveAll(accountList);
                return DepositResponse
                        .builder()
                        .message("Add Money Successfully")
                        .build();
            } else throw new AccountException("Account is not existed!!!");
        }
        else throw new UserNotFoundException("Invalid user!!!");
    }
}
