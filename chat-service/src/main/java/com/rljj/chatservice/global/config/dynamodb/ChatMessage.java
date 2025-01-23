package com.rljj.chatservice.global.config.dynamodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDateTime;

@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ChatMessage {
    private Long chatRoomId; // Partition Key: 채팅방 ID
    private LocalDateTime createdAt; // Sort Key: 생성 시간 (정렬 기준)
    private String messageId; // UUID로 생성된 메시지 ID
    private Long senderId; // 보낸 사람 ID
    private String message; // 메시지 내용

    @DynamoDbPartitionKey
    @DynamoDbAttribute("chatroom_id")
    public Long getChatRoomId() {
        return chatRoomId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("created_at")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @DynamoDbAttribute("message_id")
    public String getMessageId() {
        return messageId;
    }

    @DynamoDbAttribute("sender_id")
    public Long getSenderId() {
        return senderId;
    }

    @DynamoDbAttribute("message")
    public String getMessage() {
        return message;
    }

}

