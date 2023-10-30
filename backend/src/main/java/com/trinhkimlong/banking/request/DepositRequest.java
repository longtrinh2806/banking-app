package com.trinhkimlong.banking.request;

import lombok.Getter;

@Getter

public class DepositRequest {
    private Long depositAmount;
    private String accountNumber;
}
