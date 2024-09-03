package com.rljj.apigateway.filter;

import com.rljj.apigateway.authorization.JwtHandler;
//TODO import com.rljj.switchswitchcommon.exception.NotAuthorizationException;
import com.rljj.switchswitchcommon.jwt.JwtProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final JwtProvider jwtProvider;
    private final JwtHandler jwtHandler;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, JwtHandler jwtHandler) {
        super(Config.class);
        this.jwtProvider = jwtProvider;
        this.jwtHandler = jwtHandler;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {


            String jwt = parseJwt(exchange.getRequest());

            if (jwt == null || jwtHandler.isBlockedToken(jwt)) {
                exchange.getResponse().setStatusCode(config.getUnauthorizedStatus());
                return exchange.getResponse().setComplete();
            }

            if (jwtProvider.isExpired(jwt)) {
                jwt = jwtHandler.refreshAccessToken(jwt);
            }

            String memberId = jwtProvider.parseSubject(jwt);
            // TODO SecurityContext

            return chain.filter(exchange);
        };
    }

    private String parseJwt(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
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
