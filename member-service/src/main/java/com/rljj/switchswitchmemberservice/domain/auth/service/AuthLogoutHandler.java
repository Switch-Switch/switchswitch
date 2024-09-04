package com.rljj.switchswitchmemberservice.domain.auth.service;

import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchcommon.jwt.JwtRedisService;
import com.rljj.switchswitchmemberservice.domain.member.entity.Member;
import com.rljj.switchswitchmemberservice.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthLogoutHandler implements LogoutHandler {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final JwtRedisService jwtRedisService;

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwt = jwtProvider.parseJwt(request);
        Member member = memberService.getMember(jwtProvider.parseMemberId(jwt));
        jwtRedisService.removeRefreshToken(member.getId());
        jwtRedisService.blockAccessToken(jwt);
        jwtProvider.expireJwtInCookie(response);
    }
}
