package com.napp.notes_application.controller;

import com.napp.notes_application.dto.LoginRequestDto;
import com.napp.notes_application.dto.LoginResponseDto;
import com.napp.notes_application.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@RestController
public class AuthController {

    final private AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto
    ,@RequestHeader(value = "Authorization", required = false) String authHeader){

//       TODO
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            if (jwtUtil.validateToken(token)) {
//                return ResponseEntity.ok(new LoginResponse(token, "Already logged in"));
//            }
//        }

        Optional<String> tokenOptional = authService.authenticate(loginRequestDto);

        if(tokenOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenOptional.get();
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDto> signup(@Valid @RequestBody LoginRequestDto loginRequestDto
            ,@RequestHeader(value = "Authorization", required = false) String authHeader){

//       TODO
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            if (jwtUtil.validateToken(token)) {
//                return ResponseEntity.ok(new LoginResponse(token, "Already logged in"));
//            }
//        }

        String token = authService.signup(loginRequestDto);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

}
