package com.rljj.chatservice.global.config.dynamodb;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ChatMessage {
    @Getter(onMethod_=@DynamoDbPartitionKey)
    private String chatMessageId;

    @Getter
    private String content;
}

