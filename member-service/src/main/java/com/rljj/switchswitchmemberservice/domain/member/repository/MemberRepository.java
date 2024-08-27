package com.rljj.switchswitchmemberservice.domain.member.repository;

import com.rljj.switchswitchmemberservice.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);
}
