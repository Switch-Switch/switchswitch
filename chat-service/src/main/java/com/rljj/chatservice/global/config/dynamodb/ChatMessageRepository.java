package com.rljj.chatservice.global.config.dynamodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class ChatMessageRepository {

    private final DynamoDbTable<ChatMessage> chatMessageTable;

    public ChatMessageRepository(@Autowired DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.chatMessageTable = dynamoDbEnhancedClient.table("ChatMessage", TableSchema.fromBean(ChatMessage.class));
    }

    public void saveChatMessage(ChatMessage chatMessage) {
        chatMessageTable.putItem(chatMessage);
    }

    //조회하기
    public ChatMessage getChatMessageById(String chatMessageId) {
        return chatMessageTable.getItem(Key.builder().partitionValue(chatMessageId).build());
    }

}
