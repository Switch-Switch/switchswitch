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

    public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
        // Partition Key 조건 생성
        QueryConditional queryConditional = QueryConditional.keyEqualTo(
                Key.builder().partitionValue(chatRoomId)
                        .build());

        // Query 실행
        return chatMessageTable.query(r -> r
                .queryConditional(queryConditional)
                .scanIndexForward(false) // false로 설정하면 최신 데이터가 먼저 반환
                .limit(10)               // 최대 10개의 데이터 가져오기
        ).items().stream().toList();
    }

    /*public PaginatedResult<ChatMessage> findByChatRoomIdWithPagination(Long chatRoomId, LocalDateTime lastEvaluatedKey, int pageSize) {
        QueryEnhancedRequest.Builder queryBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(chatRoomId).build()))
                .limit(pageSize)  // 한 번에 가져올 메시지 개수 제한
                .scanIndexForward(false);  // 최신 메시지부터 정렬 (내림차순)

        // 마지막으로 가져온 메시지가 있으면 ExclusiveStartKey 설정
        if (lastEvaluatedKey != null) {
            queryBuilder.exclusiveStartKey(Map.of(
                    "chatroom_id", AttributeValue.builder().n(String.valueOf(chatRoomId)).build(),
                    "created_at", AttributeValue.builder().s(lastEvaluatedKey.toString()).build()
            ));
        }

        // 쿼리 실행
        PageIterable<ChatMessage> results = chatMessageTable.query(queryBuilder.build());

        // 첫 번째 페이지 가져오기
        Iterator<Page<ChatMessage>> iterator = results.iterator();
        if (!iterator.hasNext()) {
            return new PaginatedResult<>(Collections.emptyList(), null);
        }

        Page<ChatMessage> page = iterator.next();
        List<ChatMessage> messages = page.items();

        // 다음 페이지 조회를 위한 Key 설정
        Map<String, AttributeValue> lastKeyMap = page.lastEvaluatedKey();
        LocalDateTime nextLastKey = null;
        if (lastKeyMap != null && lastKeyMap.containsKey("created_at")) {
            nextLastKey = LocalDateTime.parse(lastKeyMap.get("created_at").s());
        }

        return new PaginatedResult<>(messages, nextLastKey);
    }

    @Getter
    @AllArgsConstructor
    public class PaginatedResult<T> {
        private List<T> items;  // 현재 페이지의 메시지 리스트
        private LocalDateTime lastEvaluatedKey;  // 다음 페이지 조회를 위한 키

         *//*
             1. 첫 페이지 요청
                 lastEvaluatedKey = null 로 요청
                 최신 메시지 pageSize개 조회
             2. 스크롤하여 추가 데이터 요청
                마지막으로 가져온 lastEvaluatedKey 값 전달
             3. 더 이상 데이터 없으면 lastEvaluated는 null
                클라이언트는 추가 요청 중단
         *//*
    }*/

    /*// 여러 chatRoomId에 대해 배치 조회 (BatchGetItem 사용)
    public List<ChatMessage> findByChatRoomIds(List<Long> chatRoomIds) {
        // BatchGetItem 요청 생성
        List<Map<String, AttributeValue>> keys = new ArrayList<>();

        chatRoomIds.forEach(chatRoomId -> {
            Map<String, AttributeValue> key = new HashMap<>();
            key.put("chatRoomId", AttributeValue.builder().n(chatRoomId.toString()).build());
            keys.add(key);
        });

        // KeysAndAttributes에 keys 추가
        Map<String, KeysAndAttributes> requestItems = new HashMap<>();
        requestItems.put("chat_messages", KeysAndAttributes.builder().keys(keys).build());

        BatchGetItemRequest batchGetItemRequest = BatchGetItemRequest.builder()
                .requestItems(requestItems)
                .build();

        // BatchGetItemResponse로 변경
        BatchGetItemResponse result = dynamoDbEnhancedClient.batchGetItem(batchGetItemRequest);

        return result.responses().get("chat_messages").stream()
                .map(item -> convertToChatMessage(item))
                .collect(Collectors.toList());
    }

    // 응답을 ChatMessage 객체로 변환하는 메서드 (필요한 변환 로직 추가)
    private ChatMessage convertToChatMessage(Map<String, AttributeValue> item) {
        // 변환 로직 추가
        return new ChatMessage(); // 적절한 변환 로직을 작성하세요
    }*/
}
