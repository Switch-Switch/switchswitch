package com.rljj.chatservice.domain.chat.controller;

import com.rljj.chatservice.domain.chat.dto.Message;
import com.rljj.chatservice.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/messages")
    public void sendMessage(Message message) {
        chatService.sendMessage(message);
    }

}
