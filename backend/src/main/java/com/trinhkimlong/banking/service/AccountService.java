package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.request.CreateAccountRequest;
import com.trinhkimlong.banking.response.CreateAccountResponse;

public interface AccountService {
    CreateAccountResponse createAccount(String token, CreateAccountRequest request) throws UserNotFoundException;

}
