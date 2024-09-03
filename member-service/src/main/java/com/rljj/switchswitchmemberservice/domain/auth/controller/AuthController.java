package com.rljj.switchswitchmemberservice.domain.auth.controller;

import com.rljj.switchswitchmemberservice.domain.auth.dto.LoginRequest;
import com.rljj.switchswitchmemberservice.domain.auth.dto.SignupRequest;
import com.rljj.switchswitchmemberservice.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return new ResponseEntity<>(authService.login(loginRequest, response), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest, HttpServletResponse response) {
        return new ResponseEntity<>(authService.signup(signupRequest, response), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestParam("jwt") String jwt, HttpServletResponse response) {
        return new ResponseEntity<>(authService.refreshAuthorization(jwt, response), HttpStatus.OK);
    }
}
