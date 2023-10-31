package com.trinhkimlong.banking.request;

import lombok.Getter;

@Getter
public class WithdrawRequest {
    private Long amount;
    private String accountNumber;
}
