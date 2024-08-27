package com.rljj.switchswitchmemberservice.domain.membertoken.service;

import com.rljj.switchswitchmemberservice.domain.member.entity.Member;
import com.rljj.switchswitchmemberservice.domain.membertoken.entity.MemberRefreshToken;

public interface MemberRefreshTokenService {
    MemberRefreshToken createRefreshToken(Member member, String refreshToken);

    MemberRefreshToken getByMember(Member member);

    void delete(Member member);
}
