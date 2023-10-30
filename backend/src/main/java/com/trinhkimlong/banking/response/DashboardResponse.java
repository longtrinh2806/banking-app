package com.trinhkimlong.banking.response;

import com.trinhkimlong.banking.model.Account;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DashboardResponse {
    private String nameAccount;
    private Long totalBalance;
    private List<Account> getUserAccount;
}
