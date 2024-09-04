package com.rljj.switchswitchmemberservice.domain.auth.service;

import com.rljj.switchswitchcommon.exception.DuplicatedException;
import com.rljj.switchswitchcommon.exception.NotAuthorizationException;
import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchcommon.jwt.JwtRedisService;
import com.rljj.switchswitchcommon.jwt.JwtSet;
import com.rljj.switchswitchmemberservice.domain.auth.dto.LoginRequest;
import com.rljj.switchswitchmemberservice.domain.auth.dto.SignupRequest;
import com.rljj.switchswitchmemberservice.domain.member.entity.Member;
import com.rljj.switchswitchmemberservice.domain.member.service.MemberService;
import com.rljj.switchswitchmemberservice.global.config.security.CustomAuthenticationManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final CustomAuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final JwtRedisService jwtRedisService;

    @Value("${jwt.expired.access-token}")
    private long accessTokenExpireTime;

    @Override
    @Transactional
    public String login(LoginRequest loginRequest, HttpServletResponse response) {
        Member member = memberService.getMemberByName(loginRequest.getName());
        authenticate(loginRequest, member);
        return handleJwt(response, member);
    }

    @Override
    @Transactional
    public String signup(SignupRequest signupRequest, HttpServletResponse response) {
        Optional<Member> member = memberService.getOpMemberByName(signupRequest.getName());
        if (member.isPresent()) {
            throw new DuplicatedException("Duplicated Member", signupRequest.getName());
        }
        return handleJwt(response, memberService.createMember(signupRequest));
    }

    @Override
    @Transactional
    public String refreshAuthorization(String accessToken, HttpServletResponse response) {
        Long memberId = jwtProvider.parseMemberIdWithoutSecure(accessToken);
        Member member = memberService.getMember(memberId);
        String refreshToken = jwtRedisService.getRefreshToken(member.getId());
        if (refreshToken == null || jwtProvider.isExpired(refreshToken)) { // 재로그인
            throw new NotAuthorizationException("Refresh token expired", String.valueOf(member.getId()));
        }
        accessToken = jwtProvider.generateToken(String.valueOf(member.getId()), accessTokenExpireTime);
        jwtProvider.setJwtInCookie(accessToken, response);
        return accessToken;
    }

    private void authenticate(LoginRequest loginRequest, Member member) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(member.getId(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    private String handleJwt(HttpServletResponse response, Member member) {
        JwtSet jwtSet = jwtProvider.generateTokenSet(member.getId());
        jwtRedisService.saveRefreshToken(member.getId(), jwtSet.getRefreshToken());
        jwtProvider.setJwtInCookie(jwtSet.getAccessToken(), response);
        return jwtSet.getAccessToken();
    }
}
