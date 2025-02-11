package com.rljj.chatservice.global.config.websocket;

import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchcommon.jwt.JwtRedisService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE + 99) // 우선 순위를 높게 설정해서 Security filter들 보다 앞서 실행되게 해준다.
@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {
    private final JwtProvider jwtProvider;
    private final JwtRedisService jwtRedisService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == StompCommand.CONNECT) {
            String token = extractToken(accessor);

            // 토큰이 없거나 차단된 경우 접근 거부
            if (isTokenInvalidOrBlocked(token)) {
                throw new AccessDeniedException("Access denied: Invalid or blocked token.");
            }

            // JWT 검증
            authenticateToken(token, accessor);
        }

        log.info("StompAccessor = {}", accessor);
        return message;
    }

    private String extractToken(StompHeaderAccessor accessor) {
        List<String> authHeaders = accessor.getNativeHeader("Authorization");
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String token = authHeaders.get(0);
            return token.startsWith("Bearer ") ? token.substring(7) : token;
        }
        return null;
    }

    private boolean isTokenInvalidOrBlocked(String token) {
        return token == null || jwtRedisService.isBlockedAccessToken(token);
    }

    private void authenticateToken(String token, StompHeaderAccessor accessor) {
        try {
            jwtProvider.validateJwt(token);
        } catch (ExpiredJwtException e) {
            throw new AccessDeniedException("Access denied: Token has expired.");
        } catch (Exception e) {
            throw new AccessDeniedException("Access denied: Invalid token.");
        }
    }

}
