package com.trinhkimlong.banking.controller;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.request.WithdrawRequest;
import com.trinhkimlong.banking.response.WithdrawResponse;
import com.trinhkimlong.banking.service.WithdrawService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/withdraw")
public class WithdrawController {
    private final WithdrawService withdrawService;

    public WithdrawController(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    @PutMapping
    public ResponseEntity<WithdrawResponse> withdraw(
            @RequestHeader("Authorization") String token,
            @RequestBody WithdrawRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(withdrawService.withdrawMoney(token, request));
    }
}
