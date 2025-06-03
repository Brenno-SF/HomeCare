package com.eng.homecare.services;

import com.eng.homecare.config.TokenService;
import com.eng.homecare.repository.UserRepository;
import com.eng.homecare.request.LoginUserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
@Service
public class AuthUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(LoginUserRequestDTO loginUserRequestDTO) throws AuthenticationException {
        var client = this.userRepository
                .findByEmail(loginUserRequestDTO.email())
                .orElseThrow(() -> new AuthenticationException("Email/Password incorrect"));

        var passwordMatches = this.passwordEncoder.matches(loginUserRequestDTO.password(), client.getPassword());

        if (!passwordMatches) throw new AuthenticationException(("Email/Password incorrect"));

        return tokenService.generateToken(client);
    }
}
