package com.napp.notes_application.service;

import com.napp.notes_application.dto.LoginRequestDto;
import com.napp.notes_application.model.User;
import com.napp.notes_application.repository.UserRepository;
import com.napp.notes_application.util.JwtUtil;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> authenticate(LoginRequestDto loginRequestDto){
        Optional<String> token = userService
                .findByEmail(loginRequestDto.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequestDto.getPassword(),u.getPassword()))
                .map(u-> jwtUtil.generateToken(u.getEmail()));

        return token ;

    }
    public String signup(LoginRequestDto loginRequestDto){
        if(userService.existsByEmail(loginRequestDto.getEmail())){
            throw new EntityExistsException("User already exists");
        }
        final String encodedPassword;
        encodedPassword = passwordEncoder.encode( loginRequestDto.getPassword());
        User user = new User();
        user.setEmail(loginRequestDto.getEmail());
        user.setPassword(encodedPassword);
        User newUser = userService.save(user);

        return jwtUtil.generateToken(user.getEmail());
    }

}
