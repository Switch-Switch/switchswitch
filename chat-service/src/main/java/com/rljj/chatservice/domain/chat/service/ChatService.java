package com.rljj.chatservice.domain.chat.service;

import com.rljj.chatservice.domain.chat.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final MessageSender sender;

    public void sendMessage(Message message) {
        // 1. accessToken으로 member 찾고

        // 2. message 객체에 보낸시간, 보낸사람 memberNo, 닉네임을 셋팅해준다.
        //message.setSendTimeAndSender(LocalDateTime.now(), findMember.getMemberNo(), findMember.getNickname(), readCount);

        // 3. 메시지를 전송한다.
        sender.send("chat", message); // TOPIC: chat 임시 설정
    }
}
