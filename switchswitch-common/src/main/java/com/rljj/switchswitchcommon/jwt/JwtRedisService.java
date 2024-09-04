package com.rljj.switchswitchcommon.jwt;

public interface JwtRedisService {
    String refreshAccessToken(String jwt);

    boolean isBlockedAccessToken(String jwt);

    void blockAccessToken(String jwt);

    void saveRefreshToken(Long memberId, String refreshToken);

    String getRefreshToken(Long memberId);

    void removeRefreshToken(Long id);
}

