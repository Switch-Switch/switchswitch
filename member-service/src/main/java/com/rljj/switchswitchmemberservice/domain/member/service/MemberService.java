package com.rljj.switchswitchmemberservice.domain.member.service;

import com.rljj.switchswitchmemberservice.domain.auth.dto.SignupRequest;
import com.rljj.switchswitchmemberservice.domain.member.entity.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> getOpMemberByName(String name);

    Member getMember(String name);

    Member getMember(Long id);

    void createMember(SignupRequest signupRequest);
}
