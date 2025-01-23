package com.rljj.switchswitchentity.chip.chipexchange;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChipExchange extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chip_post_id")
    @NonNull
    private ChipPost chipPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chip_info_id")
    @NonNull
    private ChipInfo chipInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NonNull
    private Member member;

    private String content;

    @Enumerated(EnumType.STRING)
    @NonNull
    private ChipExchangeStatus status;
}