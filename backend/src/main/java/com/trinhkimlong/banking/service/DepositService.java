package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.request.DepositRequest;
import com.trinhkimlong.banking.response.DepositResponse;

public interface DepositService {
    DepositResponse addMoney(String token, DepositRequest request) throws UserNotFoundException;
}
