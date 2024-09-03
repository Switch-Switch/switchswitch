package com.rljj.switchswitchmemberservice.global.config.jwt;

public interface JwtRedisHandler {
    void blockAccessToken(String jwt);
    void setRefreshToken(Long memberId, String refreshToken);
    String getRefreshToken(Long memberId);
    void remove(Long id);
}
