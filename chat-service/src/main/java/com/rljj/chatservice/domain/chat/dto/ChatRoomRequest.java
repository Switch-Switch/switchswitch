package com.rljj.chatservice.domain.chat.dto;

import com.rljj.switchswitchentity.chat.ChatRoom;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.member.Member;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRequest {

    @NotNull
    private Long chipPostId; // 게시물 id
    @NotNull
    private Long requesterId; // 거래요청건 멤버 id

    public ChatRoom toEntity(Long memberId) {
        return ChatRoom.create(
                ChipPost.builder().id(chipPostId).build(),
                Member.builder().id(memberId).build(),
                Member.builder().id(requesterId).build()
        );
    }
}
