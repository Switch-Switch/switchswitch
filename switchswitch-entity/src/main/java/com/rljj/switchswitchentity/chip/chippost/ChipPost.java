package com.rljj.switchswitchentity.chip.chippost;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.chip.chippost.type.ChipPostStatus;
import com.rljj.switchswitchentity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chip_post")
public class ChipPost extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "chip_info_id", nullable = false)
    private ChipInfo chipInfo;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ChipPostStatus status;

    @Builder
    public ChipPost(ChipInfo chipInfo, Member member, String title, String description, ChipPostStatus status) {
        this.chipInfo = chipInfo;
        this.member = member;
        this.title = title;
        this.description = description;
        this.status = status;
    }
}
