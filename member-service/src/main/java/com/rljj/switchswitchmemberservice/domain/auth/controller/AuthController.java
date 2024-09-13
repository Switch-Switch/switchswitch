package com.rljj.switchswitchmemberservice.domain.auth.controller;

import com.rljj.switchswitchmemberservice.domain.auth.dto.SignupRequest;
import com.rljj.switchswitchmemberservice.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestParam("jwt") String jwt, HttpServletResponse response) {
        return new ResponseEntity<>(authService.refreshAuthorization(jwt, response), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(userDetails.getUsername(), HttpStatus.OK);
    }

}
