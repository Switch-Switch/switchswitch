package com.rljj.chatservice.domain.chat.dto;

import lombok.*;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message implements Serializable {
    private String id;
    private Integer chatNo;
    private String content;
}


