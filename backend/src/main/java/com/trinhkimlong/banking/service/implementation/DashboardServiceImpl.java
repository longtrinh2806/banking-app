package com.trinhkimlong.banking.service.implementation;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.model.Account;
import com.trinhkimlong.banking.model.User;
import com.trinhkimlong.banking.repository.AccountRepository;
import com.trinhkimlong.banking.response.DashboardResponse;
import com.trinhkimlong.banking.service.UserService;
import com.trinhkimlong.banking.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    public DashboardServiceImpl(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }
    public DashboardResponse getDashboardInfo(String token) throws UserNotFoundException {
        User user = userService.findUserProfileByJwt(token);
        String userFullName = user.getFirstName() + " " + user.getLastName();

        List<Account> getUserAccount = accountRepository.findAccountsByUserId(user.getUserId());

        Long totalAccountsBalance = 0L;
        for (Account account : getUserAccount) {
            totalAccountsBalance += account.getBalance();
        }

        return DashboardResponse
                .builder()
                .nameAccount(userFullName)
                .totalBalance(totalAccountsBalance)
                .getUserAccount(getUserAccount)
                .build();
    }
}
