package com.rljj.switchswitchentity.chip.chipexchange;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class ChipExchange extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private ChipPost chipPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private ChipInfo chipInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member member;

    private String content;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ChipExchangeStatus status;

    public void updateStatus(ChipExchangeStatus status) {
        this.status = status;
    }
}