package com.rljj.switchswitchmemberservice.domain.auth.service;

import com.rljj.switchswitchmemberservice.domain.auth.dto.LoginRequest;
import com.rljj.switchswitchmemberservice.domain.auth.dto.SignupRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    String login(LoginRequest loginRequest, HttpServletResponse response);

    String signup(SignupRequest signupRequest, HttpServletResponse response);

    String refreshAuthorization(String accessToken, HttpServletResponse response);
}
