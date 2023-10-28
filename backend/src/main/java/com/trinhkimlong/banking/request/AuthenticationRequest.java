package com.trinhkimlong.banking.request;

import lombok.Getter;

@Getter
public class AuthenticationRequest {
    private String email;
    private String password;
}
