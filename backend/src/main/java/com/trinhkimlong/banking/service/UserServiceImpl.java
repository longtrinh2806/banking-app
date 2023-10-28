package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.model.User;
import com.trinhkimlong.banking.repository.UserRepository;
import com.trinhkimlong.banking.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    @Override
    public User findUserById(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            return user.get();
        throw new UserNotFoundException("User with id: " + userId + " not existed!");
    }
    @Override
    public User findUserProfileByJwt(String token) throws UserNotFoundException {
        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new UserNotFoundException("user with email: " + email + " not exist!");
        return user;
    }
}
