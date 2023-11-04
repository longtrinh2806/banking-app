package com.trinhkimlong.banking.controller;

import com.trinhkimlong.banking.request.AuthenticationRequest;
import com.trinhkimlong.banking.request.RegisterRequest;
import com.trinhkimlong.banking.response.AuthenticationResponse;
import com.trinhkimlong.banking.service.AuthenticationService;
import com.trinhkimlong.banking.service.implementation.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.logIn(request));
    }
}
