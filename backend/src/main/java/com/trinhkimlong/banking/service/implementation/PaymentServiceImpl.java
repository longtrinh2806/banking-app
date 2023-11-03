package com.trinhkimlong.banking.service.implementation;

import com.trinhkimlong.banking.exception.AccountException;
import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.model.Account;
import com.trinhkimlong.banking.model.Payment;
import com.trinhkimlong.banking.model.TransactionType;
import com.trinhkimlong.banking.model.User;
import com.trinhkimlong.banking.repository.AccountRepository;
import com.trinhkimlong.banking.repository.PaymentRepository;
import com.trinhkimlong.banking.request.PaymentRequest;
import com.trinhkimlong.banking.response.PaymentResponse;
import com.trinhkimlong.banking.response.TransactionHistoryResponse;
import com.trinhkimlong.banking.service.PaymentService;
import com.trinhkimlong.banking.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
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

                    Payment payment = new Payment();
                    payment.setPaymentId(0L);
                    payment.setAmount(request.getAmount());
                    payment.setAccount(account);
                    payment.setBeneficiaryAccount(beneficiaryAccount.getAccountNumber());
                    payment.setBeneficiaryName(beneficiaryAccount.getAccountName());
                    payment.setTransactionType(TransactionType.PAYMENT);

                    paymentRepository.save(payment);
                }
            }
            return PaymentResponse
                    .builder()
                    .message("Transfer Successfully")
                    .build();
        }
        else throw new UserNotFoundException("Invalid amount or beneficiary number!!!");
    }

    @Override
    public List<TransactionHistoryResponse> transactionHistory(String token) throws UserNotFoundException {

        User tmp = userService.findUserProfileByJwt(token);

        List<TransactionHistoryResponse> history = new ArrayList<>();

        if (tmp != null) {
            List<Account> accountList = accountRepository.findAccountsByUserId(tmp.getUserId());

            List<Long> accountIds = accountList.stream()
                    .map(Account::getAccountId)
                    .collect(Collectors.toList());

            List<Payment> payments = paymentRepository.findByAccountIds(accountIds);

            for (Payment payment : payments) {
                TransactionHistoryResponse historyResponse = new TransactionHistoryResponse();
                historyResponse.setPaymentId(payment.getPaymentId());
                historyResponse.setAccountName(payment.getAccount().getAccountName());
                historyResponse.setAccountNumber(payment.getAccount().getAccountNumber());
                historyResponse.setBeneficiaryName(payment.getBeneficiaryName());
                historyResponse.setBeneficiaryAccount(payment.getBeneficiaryAccount());
                historyResponse.setAmount(payment.getAmount());
                historyResponse.setTransactionType(payment.getTransactionType());

                history.add(historyResponse);
            }
            return history;
        } else throw new AccountException("Invalid user!!!");
    }
}
