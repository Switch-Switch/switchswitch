package com.rljj.chatservice.domain.chat.dto;

import com.rljj.chatservice.domain.chat.model.ChatMessage;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message implements Serializable {
    private String id;

    @NotNull
    private Long chatRoomId;

    @NotNull
    private String message;

    private Long senderId;
    //private Long senderNickname;

    private LocalDateTime createdAt;

    // Method to set id, senderId, and createdAt at once
    public void setMessageDetails(Long senderId, LocalDateTime createdAt) {
        this.id = UUID.randomUUID().toString();
        this.senderId = senderId;
        this.createdAt = createdAt;
    }

    // Message를 ChatMessage로 변환하는 메서드
    public ChatMessage toChatMessage() {
        return ChatMessage.builder()
                .chatRoomId(this.chatRoomId)
                .createdAt(this.createdAt)
                .messageId(this.id)
                .senderId(this.senderId)
                .message(this.message)
                .build();
    }

}


