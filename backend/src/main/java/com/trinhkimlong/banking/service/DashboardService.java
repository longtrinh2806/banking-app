package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.model.Account;
import com.trinhkimlong.banking.model.User;
import com.trinhkimlong.banking.repository.AccountRepository;
import com.trinhkimlong.banking.response.DashboardResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    public DashboardService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }
    public DashboardResponse getDashboardInfo(String token) throws UserNotFoundException {
        User user = userService.findUserProfileByJwt(token);
        String userFullName = user.getFirstName() + " " + user.getLastName();

        List<Account> getUserAccount = accountRepository.findAccountsByUserId(user.getUserId());

        Long totalAccountsBalance = accountRepository.getTotalBalance(user.getUserId());

        return DashboardResponse
                .builder()
                .nameAccount(userFullName)
                .totalBalance(totalAccountsBalance)
                .build();
    }
}
