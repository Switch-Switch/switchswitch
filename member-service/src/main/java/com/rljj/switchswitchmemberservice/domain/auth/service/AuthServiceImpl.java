package com.rljj.switchswitchmemberservice.domain.auth.service;

import com.rljj.switchswitchcommon.exception.DuplicatedException;
import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchcommon.jwt.JwtSet;
import com.rljj.switchswitchmemberservice.domain.auth.dto.LoginRequest;
import com.rljj.switchswitchmemberservice.domain.auth.dto.SignupRequest;
import com.rljj.switchswitchmemberservice.domain.member.entity.Member;
import com.rljj.switchswitchmemberservice.domain.member.service.MemberService;
import com.rljj.switchswitchmemberservice.domain.membertoken.entity.MemberRefreshToken;
import com.rljj.switchswitchmemberservice.domain.membertoken.service.MemberRefreshTokenService;
import com.rljj.switchswitchmemberservice.global.config.security.CustomAuthenticationManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final JwtProvider jwtProvider;
    private final MemberRefreshTokenService memberRefreshTokenService;
    private final MemberService memberService;

    @Value("${jwt.expired.access-token}")
    private long accessTokenExpireTime;

    @Override
    @Transactional
    public String login(LoginRequest loginRequest, HttpServletResponse response) {
        Member member = memberService.getMemberByName(loginRequest.getName());
        authenticate(loginRequest);
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
        String username = jwtProvider.parseSubjectWithoutSecure(accessToken);
        Member member = memberService.getMemberByName(username);
        String refreshToken = member.getMemberRefreshToken().getRefreshToken();
        if (jwtProvider.isExpired(refreshToken)) { // 재로그인
            throw new BadCredentialsException("Refresh token expired");
        }
        accessToken = jwtProvider.generateToken(member.getName(), accessTokenExpireTime);
        jwtProvider.setJwtInCookie(accessToken, response);
        return accessToken;
    }

    private void authenticate(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    private String handleJwt(HttpServletResponse response, Member member) {
        JwtSet jwtSet = jwtProvider.generateTokenSet(member.getName());
        MemberRefreshToken memberRefreshToken = member.getMemberRefreshToken();
        if (memberRefreshToken != null) {
            memberRefreshToken.updateExpired();
        } else {
            member.setMemberRefreshToken(
                    memberRefreshTokenService.createRefreshToken(member, jwtSet.getRefreshToken()));
        }
        jwtProvider.setJwtInCookie(jwtSet.getAccessToken(), response);
        return jwtSet.getAccessToken();
    }
}
