package com.trinhkimlong.banking.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DashboardResponse {
    private String nameAccount;
    private Long totalBalance;
}
