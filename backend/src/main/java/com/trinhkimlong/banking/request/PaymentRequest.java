package com.trinhkimlong.banking.request;

import lombok.Getter;

@Getter
public class PaymentRequest {
    private String accountNumber;
    private String beneficiaryName;
    private String beneficiaryAccount;
    private Long amount;
}
