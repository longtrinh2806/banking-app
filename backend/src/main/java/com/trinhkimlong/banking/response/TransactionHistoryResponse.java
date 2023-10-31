package com.trinhkimlong.banking.response;


import com.trinhkimlong.banking.model.TransactionType;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TransactionHistoryResponse {
    private Long paymentId;
    private String accountName;
    private String accountNumber;
    private String beneficiaryName;
    private String beneficiaryAccount;
    private Long amount;
    private TransactionType transactionType;
}
