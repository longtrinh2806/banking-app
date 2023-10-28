package com.trinhkimlong.banking.service;

import com.trinhkimlong.banking.model.Role;
import com.trinhkimlong.banking.model.User;
import com.trinhkimlong.banking.repository.UserRepository;
import com.trinhkimlong.banking.request.AuthenticationRequest;
import com.trinhkimlong.banking.request.RegisterRequest;
import com.trinhkimlong.banking.response.AuthenticationResponse;
import com.trinhkimlong.banking.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthenticationResponse register(RegisterRequest registerRequest) {

        User user = userRepository.findByEmail(registerRequest.getEmail());

        if (user == null) {
            var tmp = User.builder()
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(Role.User)
                    .build();
            userRepository.save(tmp);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    registerRequest.getEmail(), registerRequest.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateToken(authentication);

            return AuthenticationResponse
                    .builder()
                    .token(token)
                    .message("Sign Up Successfully")
                    .build();
        }
        else throw new IllegalArgumentException("Email is existed!!!");
    }

    public AuthenticationResponse logIn(AuthenticationRequest request) {
        Authentication authentication = authenticate(request.getEmail(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(authentication);

        return AuthenticationResponse
                .builder()
                .token(token)
                .message("Sign In Successfully")
                .build();
    }
    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = userRepository.findByEmail(email);
        if (userDetails == null)
            throw new BadCredentialsException("Invalid Email or Password!!!");
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Email or Password!!!");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
