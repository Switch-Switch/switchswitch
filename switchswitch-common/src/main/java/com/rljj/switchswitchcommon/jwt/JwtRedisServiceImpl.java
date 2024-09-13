package com.rljj.switchswitchcommon.jwt;

import com.rljj.switchswitchcommon.redis.RedisRepository;
import com.rljj.switchswitchcommon.redis.RedisRepositoryImpl;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class JwtRedisServiceImpl implements JwtRedisService {
    private final RedisRepository redisRepository;
    private final JwtProvider jwtProvider;

    private final String BLOCKLIST_PREFIX = "blocklist:";
    private final String REFRESH_TOKEN_PREFIX = "refresh-token:";

    public JwtRedisServiceImpl(RedisTemplate<String, String> redisTemplate, JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
        this.redisRepository = new RedisRepositoryImpl(redisTemplate);
    }

    @Override
    public boolean isBlockedAccessToken(String jwt) {
        return redisRepository.get(BLOCKLIST_PREFIX + jwt) != null;
    }

    @Override
    public void blockAccessToken(String jwt) {
        redisRepository.setWithExpire(
                BLOCKLIST_PREFIX + jwt,
                "",
                jwtProvider.getAccessTokenExpireTime(),
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void saveRefreshToken(Long memberId, String refreshToken) {
        redisRepository.setWithExpire(
                REFRESH_TOKEN_PREFIX + memberId,
                refreshToken,
                jwtProvider.getRefreshTokenExpireTime(),
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public String getRefreshToken(Long memberId) {
        return redisRepository.get(REFRESH_TOKEN_PREFIX + memberId);
    }

    @Override
    public void removeRefreshToken(Long id) {
        redisRepository.delete(REFRESH_TOKEN_PREFIX + id);
    }
}
