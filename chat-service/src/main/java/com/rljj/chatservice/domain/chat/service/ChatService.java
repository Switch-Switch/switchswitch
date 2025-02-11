package com.rljj.chatservice.domain.chat.service;

import com.rljj.chatservice.domain.chat.dto.*;
import com.rljj.chatservice.domain.chat.model.ChatMessage;
import com.rljj.chatservice.domain.chat.respository.ChatMessageRepository;
import com.rljj.chatservice.domain.chat.respository.ChatRoomRepository;
import com.rljj.chatservice.global.util.ConstantUtils;
import com.rljj.switchswitchentity.chat.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final MessageSender sender;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅방 만들기
    @Transactional
    public void makeChatRoom(Long memberId, ChatRoomRequest requestDto) {
        // 1. 유효성 체크 => feignClient

        // 2. 저장
        chatRoomRepository.save(requestDto.toEntity(memberId));
    }

    // 채팅방 리스트 조회
    public ChatRoomListResponse getChatRoomList(Long memberId) {
        List<ChatRoom> chatRoomList = chatRoomRepository.findChatRoomsByMemberId(memberId);
        return new ChatRoomListResponse(ChatRoomDto.from(chatRoomList, memberId));
    }

    // 채팅메시지 내역 조회하기
    public ChatMessageListResponse getChatMessageList(Long roomId, Long memberId) {
        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatRoomId(roomId);
        return new ChatMessageListResponse(ChatMessageDto.from(chatMessageList, memberId));
    }

    // 채팅메시지 보내기
    public void sendMessage(Message message, Authentication authentication) {

        // 1. message 객체에 필요한 정보 세팅
        message.setMessageDetails(extractMemberId(authentication), LocalDateTime.now());

        // 2. 메시지 전송
        sender.send(ConstantUtils.KAFKA_TOPIC, message);

        // 3. dynamodb 저장
        chatMessageRepository.saveChatMessage(message.toChatMessage());
    }

    private Long extractMemberId(Authentication authentication) {
        return Long.parseLong(((UserDetails) authentication.getPrincipal()).getUsername());
    }

}
