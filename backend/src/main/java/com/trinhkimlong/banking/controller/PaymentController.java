package com.trinhkimlong.banking.controller;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.request.BeneficiaryRequest;
import com.trinhkimlong.banking.request.PaymentRequest;
import com.trinhkimlong.banking.response.BeneficiaryNameResponse;
import com.trinhkimlong.banking.response.PaymentResponse;
import com.trinhkimlong.banking.response.TransactionHistoryResponse;
import com.trinhkimlong.banking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PutMapping
    public ResponseEntity<PaymentResponse> payment(
            @RequestHeader("Authorization") String token,
            @RequestBody PaymentRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(paymentService.transfer(token, request));
    }

    @GetMapping
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactionHistory(
            @RequestHeader("Authorization") String token) throws UserNotFoundException {
        return ResponseEntity.ok(paymentService.transactionHistory(token));
    }

    @PostMapping
    public ResponseEntity<BeneficiaryNameResponse> getBeneficiaryName(
            @RequestHeader("Authorization") String token,
            @RequestBody BeneficiaryRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(paymentService.getBeneficiaryName(token, request));
    }
}
