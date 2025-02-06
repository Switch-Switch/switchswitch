package com.rljj.switchswitchentity.chat;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.*;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoom extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private ChipPost chipPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member creatorUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member interestedUser;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ChatRoomStatus status;
    
}
