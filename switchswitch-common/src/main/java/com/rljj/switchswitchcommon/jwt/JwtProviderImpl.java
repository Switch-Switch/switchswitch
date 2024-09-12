package com.rljj.switchswitchcommon.jwt;

import com.rljj.switchswitchcommon.exception.NotAuthorizationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class JwtProviderImpl implements JwtProvider {

    private final long accessTokenExpireTime;
    private final long refreshTokenExpireTime;
    private final String secretKey;

    private final String TOKEN_KEY = "jwt";

    public final String GRANT_TYPE = "Bearer ";


    @Override
    public JwtSet generateTokenSet(Long memberId) {
        return JwtSet.builder()
                .accessToken(generateToken(String.valueOf(memberId), accessTokenExpireTime))
                .refreshToken(generateToken(String.valueOf(memberId), refreshTokenExpireTime))
                .build();
    }

    @Override
    public String generateToken(String subject, Long expired) {
        return Jwts.builder()
                .subject(subject)
                .expiration(new Date(System.currentTimeMillis() + expired))
                .issuedAt(new Date())
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    @Override
    public String parseSubject(String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new NotAuthorizationException(e.getMessage(), e.getClaims().getSubject());
        }
    }

    @Override
    public Long parseMemberId(String jwt) {
        return Long.parseLong(parseSubject(jwt));
    }

    @Override
    public String parseJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(GRANT_TYPE)) {
            return null;
        }
        return authHeader.substring(7);
    }

    @Override
    public String parseSubjectWithoutSecure(String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    @Override
    public Long parseMemberIdWithoutSecure(String jwt) {
        return Long.parseLong(parseSubjectWithoutSecure(jwt));
    }

    @Override
    public void validateJwt(String jwt) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getExpiration();
        } catch (SignatureException | MalformedJwtException |
                 UnsupportedJwtException | IllegalArgumentException |
                 ExpiredJwtException jwtException) {
            throw jwtException;
        }
    }

    @Override
    public void setJwtInCookie(String accessToken, HttpServletResponse response) {
        Cookie cookie = new Cookie(TOKEN_KEY, accessToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) accessTokenExpireTime);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void expireJwtInCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(TOKEN_KEY, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
