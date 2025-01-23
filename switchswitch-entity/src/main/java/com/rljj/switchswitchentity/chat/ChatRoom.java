package com.rljj.switchswitchentity.chat;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.*;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoom extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "chip_post_id", nullable = false)
    private ChipPost post;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", nullable = false)
    private Member creatorUser;

    @ManyToOne
    @JoinColumn(name = "interested_user_id", nullable = false)
    private Member interestedUser;

    @Enumerated(EnumType.STRING)
    @NonNull
    private ChatRoomStatus status;
    
}
