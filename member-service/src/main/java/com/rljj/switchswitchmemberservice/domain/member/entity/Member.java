package com.rljj.switchswitchmemberservice.domain.member.entity;

import com.rljj.switchswitchcommon.baseentity.BaseEntity;
import com.rljj.switchswitchmemberservice.domain.membertoken.entity.MemberRefreshToken;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
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
