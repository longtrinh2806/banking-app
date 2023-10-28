package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.model.User;

public interface UserService {
    User findUserById(Long userId) throws UserNotFoundException;
    User findUserProfileByJwt(String jwt) throws UserNotFoundException;
}
