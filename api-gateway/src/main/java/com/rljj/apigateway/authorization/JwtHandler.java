package com.rljj.apigateway.authorization;

public interface JwtHandler {
    String refreshAccessToken(String memberId);
    boolean isBlockedToken(String jwt);
}
