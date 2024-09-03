package com.rljj.switchswitchmemberservice.global.config.jwt;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtRedisHandlerImpl implements JwtRedisHandler {

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    private final String BLOCKLIST_PREFIX = "blocklist:";
    private final String REFRESH_TOKEN_PREFIX = "refreshtoken:";

    @Override
    public void blockAccessToken(String jwt) {
        valueOperations.set(BLOCKLIST_PREFIX + jwt, "");
    }

    @Override
    public void setRefreshToken(Long memberId, String refreshToken) {
        valueOperations.set(REFRESH_TOKEN_PREFIX + memberId, refreshToken);
    }

    @Override
    public String getRefreshToken(Long memberId) {
        return valueOperations.get(REFRESH_TOKEN_PREFIX + memberId);
    }

    @Override
    public void remove(Long id) {
        valueOperations.getAndDelete(REFRESH_TOKEN_PREFIX + id);
    }
}
