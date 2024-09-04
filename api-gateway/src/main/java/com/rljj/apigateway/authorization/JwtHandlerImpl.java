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
    private ValueOperations<String, String> valueOperations;

    private final JwtProvider jwtProvider;

    @Value("${jwt.expired.access-token}")
    private long accessTokenExpireTime;

    private final String BLOCKLIST_PREFIX = "blocklist:";
    private final String REFRESHTOKEN_PREFIX = "refreshtoken:";

    @Override
    public String refreshAccessToken(String jwt) {
        String memberId = jwtProvider.parseSubject(jwt);
        String refreshToken = valueOperations.get(REFRESHTOKEN_PREFIX + memberId);
        if (refreshToken == null || jwtProvider.isExpired(refreshToken)) {
            valueOperations.getAndDelete(memberId);
            throw new NotAuthorizationException("Refresh token is expired", memberId);
        }
        return jwtProvider.generateToken(memberId, accessTokenExpireTime);
    }

    @Override
    public boolean isBlockedToken(String jwt) {
        return valueOperations.get(BLOCKLIST_PREFIX + jwt) != null;
    }

}
