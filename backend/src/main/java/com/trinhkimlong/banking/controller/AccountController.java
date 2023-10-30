package com.trinhkimlong.banking.controller;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.request.CreateAccountRequest;
import com.trinhkimlong.banking.response.CreateAccountResponse;
import com.trinhkimlong.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<CreateAccountResponse> createNewAccount(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateAccountRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(accountService.createAccount(token, request));
    }
}
