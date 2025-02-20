package com.rljj.chatservice.domain.chat.dto;

import com.rljj.switchswitchentity.chat.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChatRoomDto {

    private Long chatRoomId;
    private Long memberId; // 현재 멤버 ID
    private Long chatPartnerId; // 채팅 상대방 ID
    private Long chipPostId; // 게시물 ID
//    private String latestMessage;
//    private LocalDateTime latestMessageCreatedAt;

    public ChatRoomDto(ChatRoom chatRoom, Long memberId) {
        this.chatRoomId = chatRoom.getId();
        this.memberId = memberId;
        this.chatPartnerId = chatRoom.getAuthor().getId().equals(memberId)
                ? chatRoom.getRequester().getId()  // 내가 author 상대방 = requester
                : chatRoom.getAuthor().getId();
        this.chipPostId = chatRoom.getChipPost().getId();
    }

    public static List<ChatRoomDto> from(List<ChatRoom> chatRoomList, Long memberId) {
        return chatRoomList.stream()
                .map(chatRoom -> new ChatRoomDto(chatRoom, memberId))
                .toList();
    }
}
