package com.rljj.chipservice.domain.post.dto;

import com.rljj.switchswitchcommon.exception.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class PostResponse {

    private final Boolean success;
    private final Integer code;
    private final String message;

    public static PostResponse of(Boolean success, Code code) {
        return new PostResponse(success, code.getCode(), code.getMessage());
    }

    public static PostResponse of(Boolean success, Code errorCode, Exception e) {
        return new PostResponse(success, errorCode.getCode(), errorCode.getMessage(e));
    }

    public static PostResponse of(Boolean success, Code errorCode, String message) {
        return new PostResponse(success, errorCode.getCode(), errorCode.getMessage(message));
    }
}