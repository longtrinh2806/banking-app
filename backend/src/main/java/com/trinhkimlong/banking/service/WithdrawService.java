package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.request.WithdrawRequest;
import com.trinhkimlong.banking.response.WithdrawResponse;

public interface WithdrawService {
    WithdrawResponse withdrawMoney(String token, WithdrawRequest request) throws UserNotFoundException;
}
