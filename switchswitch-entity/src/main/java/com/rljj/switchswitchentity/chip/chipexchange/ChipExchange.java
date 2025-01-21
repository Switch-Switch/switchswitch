package com.rljj.switchswitchentity.chip.chipexchange;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.chip.chipexchange.type.ChipExchangeStatus;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chip_exchange")
public class ChipExchange extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "chip_post_id", nullable = false)
    private ChipPost chipPost;

    @ManyToOne
    @JoinColumn(name = "chip_info_id", nullable = false)
    private ChipInfo chipInfo;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ChipExchangeStatus status;

    @Builder
    public ChipExchange(ChipPost chipPost, ChipInfo chipInfo, Member member, String content, ChipExchangeStatus status) {
        this.chipPost = chipPost;
        this.chipInfo = chipInfo;
        this.content = content;
        this.member = member;
        this.status = status;
    }
}