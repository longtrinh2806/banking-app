package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.request.PaymentRequest;
import com.trinhkimlong.banking.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse transfer(String token, PaymentRequest request) throws UserNotFoundException;
}
