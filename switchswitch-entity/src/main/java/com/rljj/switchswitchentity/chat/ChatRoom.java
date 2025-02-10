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
public class ChatRoom extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private ChipPost chipPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member requester;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ChatRoomStatus status;

    public static ChatRoom create(ChipPost chipPost, Member author, Member requester) {
        return new ChatRoom(chipPost, author, requester);
    }

    @Builder
    private ChatRoom(ChipPost chipPost, Member author, Member requester) {
        this.chipPost = chipPost;
        this.author = author;
        this.requester = requester;
        this.status = ChatRoomStatus.ACTIVE;
    }
    
}
