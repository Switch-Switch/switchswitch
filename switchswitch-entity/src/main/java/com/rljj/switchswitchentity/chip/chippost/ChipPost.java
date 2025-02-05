package com.rljj.switchswitchentity.chip.chippost;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.member.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class ChipPost extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private ChipInfo chipInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private Member member;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NonNull
    private ChipPostStatus status;
}
