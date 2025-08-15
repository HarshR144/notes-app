package com.napp.notes_application.controller;

import com.napp.notes_application.dto.LoginRequestDto;
import com.napp.notes_application.dto.LoginResponseDto;
import com.napp.notes_application.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@Slf4j
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

        log.info("Signup request received for email: {}", loginRequestDto.getEmail());

        try {
            String token = authService.signup(loginRequestDto);
            log.info("Signup successful for email: {}", loginRequestDto.getEmail());
            return ResponseEntity.ok(new LoginResponseDto(token));
        } catch (Exception e) {
            log.error("Signup failed for email: {}", loginRequestDto.getEmail(), e);
            return ResponseEntity.badRequest().build();
        }
    }
    // Add an OPTIONS handler if needed (though Spring should handle this automatically)
    @RequestMapping(value = {"/login", "/signup"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }

}
