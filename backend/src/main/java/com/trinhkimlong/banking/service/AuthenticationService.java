package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.request.AuthenticationRequest;
import com.trinhkimlong.banking.request.RegisterRequest;
import com.trinhkimlong.banking.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse logIn(AuthenticationRequest request);
}
