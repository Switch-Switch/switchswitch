package com.rljj.switchswitchcommon.jwt;

import com.rljj.switchswitchcommon.exception.NotAuthorizationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public class JwtRedisServiceImpl implements JwtRedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOperations;
    private final JwtProvider jwtProvider;

    private final String BLOCKLIST_PREFIX = "blocklist:";
    private final String REFRESH_TOKEN_PREFIX = "refresh-token:";

    public JwtRedisServiceImpl(RedisTemplate<String, String> redisTemplate, JwtProvider jwtProvider) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String refreshAccessToken(String jwt) {
        String memberId = jwtProvider.parseSubjectWithoutSecure(jwt);
        String refreshToken = valueOperations.get(REFRESH_TOKEN_PREFIX + memberId);
        if (refreshToken == null || jwtProvider.isExpired(refreshToken)) {
            valueOperations.getAndDelete(memberId);
            throw new NotAuthorizationException("Refresh token is expired", memberId);
        }
        return jwtProvider.generateToken(memberId, jwtProvider.getAccessTokenExpireTime());
    }

    @Override
    public boolean isBlockedAccessToken(String jwt) {
        return valueOperations.get(BLOCKLIST_PREFIX + jwt) != null;
    }

    @Override
    public void blockAccessToken(String jwt) {
        saveWithExpire(BLOCKLIST_PREFIX + jwt, "", jwtProvider.getAccessTokenExpireTime());
    }

    @Override
    public void saveRefreshToken(Long memberId, String refreshToken) {
        saveWithExpire(REFRESH_TOKEN_PREFIX + memberId, refreshToken, jwtProvider.getRefreshTokenExpireTime());
    }

    @Override
    public String getRefreshToken(Long memberId) {
        return valueOperations.get(REFRESH_TOKEN_PREFIX + memberId);
    }

    @Override
    public void removeRefreshToken(Long id) {
        valueOperations.getAndDelete(REFRESH_TOKEN_PREFIX + id);
    }

    private void saveWithExpire(String key, String value, long expireMilliSecond) {
        valueOperations.set(key, value);
        redisTemplate.expire(key, expireMilliSecond, TimeUnit.MILLISECONDS);
    }
}
