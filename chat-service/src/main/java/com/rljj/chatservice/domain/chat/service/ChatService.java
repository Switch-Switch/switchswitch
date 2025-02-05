package com.rljj.chatservice.domain.chat.service;

import com.rljj.chatservice.domain.chat.dto.*;
import com.rljj.chatservice.domain.chat.model.ChatMessage;
import com.rljj.chatservice.domain.chat.respository.ChatMessageRepository;
import com.rljj.chatservice.domain.chat.respository.ChatRoomRepository;
import com.rljj.switchswitchentity.chat.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ChatRoomResponse> getChatRoomList(Long memberId) {
        List<ChatRoom> chatRoomList = chatRoomRepository.findChatRoomsByMemberId(memberId);

        List<ChatRoomResponse> chatRoomResponseList = chatRoomList.stream()
                .map(chatRoom -> new ChatRoomResponse(
                        chatRoom.getId(),
                        memberId,  // 현재 로그인한 사용자 ID
                        chatRoom.getCreatorUser().getId().equals(memberId)
                                ? chatRoom.getInterestedUser().getId()  // 내가 creatorUser면 상대방 = interestedUser
                                : chatRoom.getCreatorUser().getId(),     // 내가 interestedUser면 상대방 = creatorUser
                        chatRoom.getChipPost().getId()
                ))
                .toList();

        // TODO 다이나모 DB 조회해서 마지막 메시지 resposne에 넣어줘야 함
        return chatRoomResponseList;
    }

    // 채팅메시지 내역 조회하기
    public ChatMessageListResponse getChatMessageList(Long roomId, Long memberId) {
        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatRoomId(roomId);
        return new ChatMessageListResponse(ChatMessageDto.from(chatMessageList, memberId));
    }

    // 채팅메시지 보내기
    public void sendMessage(Message message) {
        // 1. accessToken으로 member 찾고

        // 2. message 객체에 보낸시간, 보낸사람 memberNo, 닉네임을 셋팅해준다.
        //message.setSendTimeAndSender(LocalDateTime.now(), findMember.getMemberNo(), findMember.getNickname(), readCount);

        // 3. 메시지를 전송한다.
        sender.send("chat", message); // TOPIC: chat 임시 설정
    }

}
