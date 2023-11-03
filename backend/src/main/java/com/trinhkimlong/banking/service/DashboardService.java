package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.response.DashboardResponse;

public interface DashboardService {
    DashboardResponse getDashboardInfo(String token) throws UserNotFoundException;
}
