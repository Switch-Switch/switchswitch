package com.rljj.chatservice.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomResponse {

    private Long chatRoomId;
    private Long memberId; // 현재 멤버 ID
    private Long chatPartnerId; // 채팅 상대방 ID
    private Long chipPostId; // 게시물 ID
//    private String latestMessage;
//    private LocalDateTime latestMessageCreatedAt;
}
