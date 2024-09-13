package com.rljj.apigateway.filter;

import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchcommon.jwt.JwtRedisService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final JwtProvider jwtProvider;
    private final JwtRedisService jwtRedisService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, JwtRedisService jwtRedisService) {
        super(Config.class);
        this.jwtProvider = jwtProvider;
        this.jwtRedisService = jwtRedisService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("filter start >>>>>>>>>>");
            ServerHttpRequest request = exchange.getRequest();
            String jwt = extractTokenFromHeader(request.getHeaders());

            if (jwt == null || jwtRedisService.isBlockedAccessToken(jwt)) {
                exchange.getResponse().setStatusCode(config.getUnauthorizedStatus());
                return exchange.getResponse().setComplete();
            }

            jwtProvider.validateJwt(jwt);

            return chain.filter(exchange);
        };
    }

    private String extractTokenFromHeader(HttpHeaders headers) {
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }

    @Setter
    @Getter
    public static class Config {
        // 설정이 필요한 경우 이 클래스에 추가 가능
        private HttpStatus unauthorizedStatus = HttpStatus.UNAUTHORIZED;
    }
}
