package com.rljj.chatservice.domain.chat.controller;

import com.rljj.chatservice.domain.chat.respository.ChatMessageRepository;
import com.rljj.chatservice.domain.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {

    private final ChatMessageRepository chatMessageRepository;

    @PostMapping("/chatMessage")
    public ResponseEntity<Void> saveChatMessage(@RequestBody ChatMessage chatMessage) {
        chatMessageRepository.saveChatMessage(chatMessage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chatMessage/{chatMessageId}")
    public ChatMessage getChatMessageById(@PathVariable("chatMessageId") String chatMessageId) {
        return chatMessageRepository.getChatMessageById(chatMessageId);
    }

    @GetMapping("/chatRoom/{chatRoomId}")
    public List<ChatMessage> getChatRoomById(@PathVariable("chatRoomId") Long chatRoomId) {
        return chatMessageRepository.getChatRoomById(chatRoomId);
    }

}
