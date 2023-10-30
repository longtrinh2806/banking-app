package com.trinhkimlong.banking.controller;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.request.DepositRequest;
import com.trinhkimlong.banking.response.DepositResponse;
import com.trinhkimlong.banking.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deposit")
public class DepositController {
    private final DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping
    public ResponseEntity<DepositResponse> deposit(
            @RequestHeader("Authorization") String token,
            @RequestBody DepositRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(depositService.addMoney(token, request));
    }
}
