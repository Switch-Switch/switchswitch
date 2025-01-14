package com.rljj.memberservice.domain.auth.service;

import com.rljj.memberservice.domain.auth.dto.SignupRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    void signup(SignupRequest signupRequest);

    String refreshAuthorization(String accessToken, HttpServletResponse response);

    void updateRefreshToken(Long memberId, String refreshToken);
}
