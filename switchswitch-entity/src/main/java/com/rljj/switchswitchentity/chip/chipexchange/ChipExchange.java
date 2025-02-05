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
    @NonNull
    private ChipPost chipPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private ChipInfo chipInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private Member member;

    private String content;

    @Enumerated(EnumType.STRING)
    @NonNull
    private ChipExchangeStatus status;

    public void updateStatus(ChipExchangeStatus status) {
        this.status = status;
    }
}