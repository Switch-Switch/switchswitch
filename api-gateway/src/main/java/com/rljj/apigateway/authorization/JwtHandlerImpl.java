package com.rljj.apigateway.authorization;

import com.rljj.switchswitchcommon.exception.NotAuthorizationException;
import com.rljj.switchswitchcommon.jwt.JwtProvider;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtHandlerImpl implements JwtHandler {

    @Resource(name = "redisTemplate")
    private final ValueOperations<String, String> valueOperations;

    private final JwtProvider jwtProvider;

    @Value("${jwt.expired.access-token}")
    private long accessTokenExpireTime;

    @Override
    public String refreshAccessToken(String memberId) {
        String refreshToken = valueOperations.get(memberId);
        if (jwtProvider.isExpired(refreshToken)) {
            throw new NotAuthorizationException("Refresh token is expired", memberId);
        }

        return jwtProvider.generateToken(memberId, accessTokenExpireTime);
    }

    @Override
    public boolean isBlockedToken(String jwt) {
        return valueOperations.get(jwt) != null;
    }
}
