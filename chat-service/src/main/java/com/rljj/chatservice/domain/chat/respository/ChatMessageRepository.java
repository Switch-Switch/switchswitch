package com.rljj.chatservice.domain.chat.respository;

import com.rljj.chatservice.domain.chat.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;

@Repository
public class ChatMessageRepository {

    private final DynamoDbTable<ChatMessage> chatMessageTable;

    public ChatMessageRepository(@Autowired DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.chatMessageTable = dynamoDbEnhancedClient.table("chat_messages", TableSchema.fromBean(ChatMessage.class));
    }

    public void saveChatMessage(ChatMessage chatMessage) {
        chatMessageTable.putItem(chatMessage);
    }

    public List<ChatMessage> getChatRoomById(Long chatRoomId) {
        // Partition Key 조건 생성
        QueryConditional queryConditional = QueryConditional.keyEqualTo(
                Key.builder().partitionValue(chatRoomId).build()
        );

        // Query 실행
        return chatMessageTable.query(r -> r
                .queryConditional(queryConditional)
                .scanIndexForward(false) // false로 설정하면 최신 데이터가 먼저 반환
                .limit(10)               // 최대 10개의 데이터 가져오기
        ).items().stream().toList();
    }
}
