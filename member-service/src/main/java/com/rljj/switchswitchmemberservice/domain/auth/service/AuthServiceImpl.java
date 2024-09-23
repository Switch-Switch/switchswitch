package com.rljj.switchswitchmemberservice.domain.auth.service;

import com.rljj.switchswitchcommon.exception.DuplicatedException;
import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchcommon.jwt.JwtRedisService;
import com.rljj.switchswitchmemberservice.domain.auth.dto.SignupRequest;
import com.rljj.switchswitchmemberservice.domain.member.entity.Member;
import com.rljj.switchswitchmemberservice.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final JwtRedisService jwtRedisService;

    @Value("${jwt.expired.access-token}")
    private long accessTokenExpireTime;

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest) {
        Optional<Member> member = memberService.getOpMemberByName(signupRequest.getName());
        if (member.isPresent()) {
            throw new DuplicatedException("Duplicated Member", signupRequest.getName());
        }
        memberService.createMember(signupRequest);
    }

    @Override
    @Transactional
    public String refreshAuthorization(String accessToken, HttpServletResponse response) {
        Long memberId = jwtProvider.parseMemberIdWithoutSecure(accessToken);
        Member member = memberService.getMember(memberId);
        String refreshToken = jwtRedisService.getRefreshToken(member.getId());

        jwtProvider.validateJwt(refreshToken);

        accessToken = jwtProvider.generateToken(String.valueOf(member.getId()), accessTokenExpireTime);
        jwtProvider.setJwtInCookie(accessToken, response);
        return accessToken;
    }

    @Override
    @Transactional
    public void updateRefreshToken(Long memberId, String refreshToken) {
        jwtRedisService.saveRefreshToken(memberId, refreshToken);
    }

}
