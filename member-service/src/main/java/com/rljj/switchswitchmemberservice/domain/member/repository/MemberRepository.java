package com.rljj.switchswitchmemberservice.domain.member.repository;

import com.rljj.switchswitchentity.member.Member;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @NonNull Optional<Member> findById(@NonNull Long id);

    Optional<Member> findByName(String name);
}
