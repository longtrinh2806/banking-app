package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.request.BeneficiaryRequest;
import com.trinhkimlong.banking.request.PaymentRequest;
import com.trinhkimlong.banking.response.BeneficiaryNameResponse;
import com.trinhkimlong.banking.response.PaymentResponse;
import com.trinhkimlong.banking.response.TransactionHistoryResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse transfer(String token, PaymentRequest request) throws UserNotFoundException;

    List<TransactionHistoryResponse> transactionHistory(String token) throws UserNotFoundException;

    BeneficiaryNameResponse getBeneficiaryName(String token, BeneficiaryRequest request) throws UserNotFoundException;
}
