package com.rljj.chatservice.domain.chat.dto;

import com.rljj.chatservice.domain.chat.model.ChatMessage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ChatMessageDto {
    private String chatMessageId;

    private Long chatRoomId;
    private LocalDateTime sendDate;

    private Long senderId;
    private String message;

    private boolean isMine;

    public ChatMessageDto(ChatMessage chatMessage, Long memberId) {
        this.chatMessageId = chatMessage.getMessageId();
        this.chatRoomId = chatMessage.getChatRoomId();
        this.sendDate = chatMessage.getCreatedAt();
        this.senderId = chatMessage.getSenderId();
        this.message = chatMessage.getMessage();
        this.isMine = chatMessage.getSenderId().equals(memberId); // 내가 보낸 메시지 여부
    }

    public static List<ChatMessageDto> from(List<ChatMessage> chatMessageList, Long memberId) {
        return chatMessageList.stream()
                .map(chatMessage -> new ChatMessageDto(chatMessage, memberId))
                .toList();
    }

}
