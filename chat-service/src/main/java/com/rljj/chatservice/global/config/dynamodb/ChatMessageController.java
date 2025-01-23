package com.rljj.chatservice.global.config.dynamodb;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("test!!!!!!!!!!!");
        return ResponseEntity.ok("hi");
    }
}
