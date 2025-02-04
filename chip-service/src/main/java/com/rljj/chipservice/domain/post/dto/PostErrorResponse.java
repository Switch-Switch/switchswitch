package com.rljj.chipservice.domain.post.dto;

import com.rljj.switchswitchcommon.exception.Code;

public class PostErrorResponse extends PostResponse{

    private PostErrorResponse(Code errorCode) {
        super(false, errorCode.getCode(), errorCode.getMessage());
    }

    private PostErrorResponse(Code errorCode, Exception e) {
        super(false, errorCode.getCode(), errorCode.getMessage(e));
    }

    private PostErrorResponse(Code errorCode, String message) {
        super(false, errorCode.getCode(), errorCode.getMessage(message));
    }


    public static PostErrorResponse of(Code errorCode) {
        return new PostErrorResponse(errorCode);
    }

    public static PostErrorResponse of(Code errorCode, Exception e) {
        return new PostErrorResponse(errorCode, e);
    }

    public static PostErrorResponse of(Code errorCode, String message) {
        return new PostErrorResponse(errorCode, message);
    }
}
