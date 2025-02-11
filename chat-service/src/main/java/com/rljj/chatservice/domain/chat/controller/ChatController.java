package com.rljj.chatservice.domain.chat.controller;

import com.rljj.chatservice.domain.chat.dto.ChatMessageListResponse;
import com.rljj.chatservice.domain.chat.dto.ChatRoomRequest;
import com.rljj.chatservice.domain.chat.dto.ChatRoomResponse;
import com.rljj.chatservice.domain.chat.dto.Message;
import com.rljj.chatservice.domain.chat.service.ChatService;
import com.rljj.chatservice.global.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // 채팅방 만들기
    @PostMapping("/rooms")
    public ResponseEntity<Void> createChatRoom(@RequestBody ChatRoomRequest request) {
        chatService.makeChatRoom(SecurityUtils.getMemberId(), request);
        return ResponseEntity.ok().build();
    }

    // TODO 응답 정리하기
    // 채팅방 리스트 조회하기
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomResponse>> getChatRoomList() {
        List<ChatRoomResponse> chatRoomList = chatService.getChatRoomList(SecurityUtils.getMemberId());
        return ResponseEntity.ok(chatRoomList);
    }

    // TODO 채팅방 삭제하기

    // 채팅메시지 내역 조회하기
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<ChatMessageListResponse> getChatMessageList(@PathVariable("roomId") Long roomId) {
        ChatMessageListResponse chatMessageList = chatService.getChatMessageList(roomId, SecurityUtils.getMemberId());
        return ResponseEntity.ok(chatMessageList);
    }

    // 채팅메시지 보내기
    @MessageMapping("/messages")
    public void sendMessage(Message message, Authentication authentication) {
        chatService.sendMessage(message, authentication);
    }

}
