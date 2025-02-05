package com.rljj.chatservice.domain.chat.controller;

import com.rljj.chatservice.domain.chat.dto.*;
import com.rljj.chatservice.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    // TODO 파라미터 memberId 임시 -> security로 처리할 것
    // 채팅방 만들기
    @PostMapping("/rooms")
    public ResponseEntity<Void> createChatRoom(
            @RequestParam Long memberId,
            @RequestBody ChatRoomRequest request) {
        chatService.makeChatRoom(memberId, request);
        return ResponseEntity.ok().build();
    }

    // TODO 일단 all 조회로 만들기 -> 페이징 처리 어캐 할지
    // 채팅방 리스트 조회하기
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomResponse>> getChatRoomList(
            @RequestParam Long memberId) {
        List<ChatRoomResponse> chatRoomList = chatService.getChatRoomList(memberId);
        return ResponseEntity.ok(chatRoomList);
    }

    // TODO 채팅방 삭제하기
    // TODO 채팅방 접속 끊기

    // 채팅메시지 내역 조회하기
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<ChatMessageListResponse> getChatMessageList(
            @PathVariable("roomId") Long roomId,
            @RequestParam Long memberId) {
        ChatMessageListResponse chatMessageList = chatService.getChatMessageList(roomId, memberId);
        return ResponseEntity.ok(chatMessageList);
    }

    // 채팅메시지 보내기
    @MessageMapping("/messages")
    public void sendMessage(Message message) {
        chatService.sendMessage(message);
    }

}
