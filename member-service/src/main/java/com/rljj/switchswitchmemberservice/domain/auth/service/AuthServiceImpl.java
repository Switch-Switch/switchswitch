package com.rljj.switchswitchmemberservice.domain.auth.service;

import com.rljj.switchswitchcommon.exception.DuplicatedException;
import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchcommon.jwt.JwtRedisService;
import com.rljj.switchswitchmemberservice.domain.auth.dto.SignupRequest;
import com.rljj.switchswitchmemberservice.domain.member.entity.Member;
import com.rljj.switchswitchmemberservice.domain.member.service.MemberService;
import com.rljj.switchswitchmemberservice.global.config.security.CustomAuthenticationManager;
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

    private final CustomAuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final JwtRedisService jwtRedisService;

    @Value("${jwt.expired.access-token}")
    private long accessTokenExpireTime;

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest, HttpServletResponse response) {
        Optional<Member> member = memberService.getOpMemberByName(signupRequest.getName());
        if (member.isPresent()) {
            throw new DuplicatedException("Duplicated Member", signupRequest.getName());
        }

        // TODO return void로 잠시 바꿔둠, HttpServletResponse 필요성
        memberService.createMember(signupRequest);
    }

    @Override
    @Transactional
    public String refreshAuthorization(String accessToken, HttpServletResponse response) {
        Long memberId = jwtProvider.parseMemberIdWithoutSecure(accessToken);
        Member member = memberService.getMember(memberId);
        String refreshToken = jwtRedisService.getRefreshToken(member.getId());

        // TODO isExpired -> void validateJwt로 변경됨
        /*if (refreshToken == null || jwtProvider.isExpired(refreshToken)) { // 재로그인
            // 만료됐으면 refresh 삭제하는 로직도 추가해야 함 (레디스 안에 설정되어 있으면 ok)
            throw new NotAuthorizationException("Refresh token expired", String.valueOf(member.getId()));
        }*/

        accessToken = jwtProvider.generateToken(String.valueOf(member.getId()), accessTokenExpireTime);
        jwtProvider.setJwtInCookie(accessToken, response);
        return accessToken;
    }

    @Override
    @Transactional
    public void updateRefreshToken(Long memberId, String refreshToken) {
        Member member = memberService.getMember(memberId);

        jwtRedisService.saveRefreshToken(memberId, refreshToken);
    }

    // TODO 삭제 예정
    /*private void authenticate(LoginRequest loginRequest, Member member) {
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
    }*/

}
