package com.rljj.switchswitchmemberservice.global.config.jwt;

import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchcommon.jwt.JwtSet;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtProviderTest {

    @Autowired
    public JwtProvider jwtProvider;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expired.refresh-token}")
    private Long expired;

    @Test
    public void testGenerateJwt() {
        String jwt = jwtProvider.generateToken("1", expired);
        assertThat(jwt).isNotBlank();
    }

    @Test
    public void testGenerateSecretKey() {
        byte[] keyBytes = Base64.getUrlDecoder().decode(secret);
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
        System.out.println(secretKey);
    }

    @Test
    public void testJWT() {
        //given
        Long givenId = 1L;
        JwtSet jwtSet = jwtProvider.generateTokenSet(givenId);

        //when
        Long resultId = jwtProvider.parseMemberId(jwtSet.getAccessToken());

        //then
        assertThat(resultId).isEqualTo(givenId);
    }
}
