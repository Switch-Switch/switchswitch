package com.rljj.switchswitchentity.member;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member extends BaseEntity {
    @Column(unique = true)
    @NonNull
    private String email;

    @Column(unique = true)
    @NonNull
    private String nickname;

    @NonNull
    private String password;
}