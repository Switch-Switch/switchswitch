package com.rljj.memberservice.domain.member.service;

import com.rljj.memberservice.domain.auth.dto.SignupRequest;
import com.rljj.memberservice.domain.member.entity.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> getOpMemberByName(String name);

    Member getMember(String name);

    Member getMember(Long id);

    void createMember(SignupRequest signupRequest);
}
