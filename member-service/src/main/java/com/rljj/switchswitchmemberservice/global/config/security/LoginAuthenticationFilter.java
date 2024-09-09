package com.rljj.switchswitchmemberservice.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchcommon.jwt.JwtSet;
import com.rljj.switchswitchmemberservice.domain.auth.dto.LoginRequest;
import com.rljj.switchswitchmemberservice.domain.auth.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    // login request 판단
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("LoginAuthenticationFilter attemptAuthentication >>>>>>>>>>");
        Authentication authentication;

        try {
            LoginRequest credential = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credential.getName(), credential.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return authentication;
    }

    // login success 이후 토큰 생성
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("LoginAuthenticationFilter successfulAuthentication >>>>>>>>>>");

        // User 불러오기
        User user = (User) authResult.getPrincipal();
        Long userId = Long.valueOf(user.getUsername());

        // 토큰 발급 및 쿠키 설정
        JwtSet jwtSet = jwtProvider.generateTokenSet(userId);
        authService.updateRefreshToken(userId, jwtSet.getRefreshToken());
        jwtProvider.setJwtInCookie(jwtSet.getAccessToken(), response);

        //super.successfulAuthentication(request, response, chain, authResult);
    }

}
