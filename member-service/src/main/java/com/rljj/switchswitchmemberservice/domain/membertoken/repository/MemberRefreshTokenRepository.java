package com.rljj.switchswitchmemberservice.domain.membertoken.repository;

import com.rljj.switchswitchmemberservice.domain.member.entity.Member;
import com.rljj.switchswitchmemberservice.domain.membertoken.entity.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {
    MemberRefreshToken findByMember(Member member);

    void deleteByMember(Member member);
}
