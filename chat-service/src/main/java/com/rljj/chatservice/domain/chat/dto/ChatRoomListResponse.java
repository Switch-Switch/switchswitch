package com.rljj.chatservice.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChatRoomListResponse {
    private List<ChatRoomDto> rooms;
}
