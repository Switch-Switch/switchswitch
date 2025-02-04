package com.rljj.memberservice.domain.member.service;

import com.rljj.switchswitchcommon.exception.NotFoundException;
import com.rljj.switchswitchentity.member.Member;
import com.rljj.memberservice.domain.auth.dto.SignupRequest;
import com.rljj.memberservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Override
    public Optional<Member> getOpMemberByName(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member getMember(String email) {
        return getOpMemberByName(email).orElseThrow(() -> new NotFoundException("Not Found User: " + email));
    }

    @Override
    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found User: " + id));
    }

    @Override
    public void createMember(SignupRequest signupRequest) {
        memberRepository.save(Member.builder()
                .email(signupRequest.getEmail())
                .password(encoder.encode(signupRequest.getPassword()))
                .nickname(signupRequest.getNickname())
                .build());
    }
}
