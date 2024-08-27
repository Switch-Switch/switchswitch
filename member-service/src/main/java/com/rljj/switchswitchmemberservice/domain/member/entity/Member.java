package com.rljj.switchswitchmemberservice.domain.member.entity;

import com.rljj.switchswitchmemberservice.domain.membertoken.entity.MemberRefreshToken;
import com.rljj.switchswitchmemberservice.global.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    private MemberRefreshToken memberRefreshToken;
}
