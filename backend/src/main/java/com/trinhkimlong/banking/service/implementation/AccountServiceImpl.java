package com.trinhkimlong.banking.service.implementation;

import com.trinhkimlong.banking.exception.AccountException;
import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.model.Account;
import com.trinhkimlong.banking.model.User;
import com.trinhkimlong.banking.repository.AccountRepository;
import com.trinhkimlong.banking.request.CreateAccountRequest;
import com.trinhkimlong.banking.response.CreateAccountResponse;
import com.trinhkimlong.banking.service.AccountService;
import com.trinhkimlong.banking.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserService userService;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(UserService userService, AccountRepository accountRepository) {
        this.userService = userService;
        this.accountRepository = accountRepository;
    }

    @Override
    public CreateAccountResponse createAccount(String token, CreateAccountRequest request) throws UserNotFoundException {

        User tmp = userService.findUserProfileByJwt(token);
        Account accountTmp = accountRepository.findByAccountNumber(request.getAccountNumber());

        if (tmp != null && accountTmp == null) {

                String name = tmp.getFirstName() + " " + tmp.getLastName();

                Account account = new Account();
                account.setAccountId(0L);
                account.setAccountNumber(request.getAccountNumber());
                account.setAccountName(name);
                account.setBalance(0L);
                account.setUser(tmp);
                accountRepository.save(account);
                return CreateAccountResponse
                        .builder()
                        .message("Create account successfully")
                        .build();
            }
        else throw new AccountException("Account existed!!!");
    }
}
