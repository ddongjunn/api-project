package com.jobis.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.Duration;
import java.time.Instant;


@Service
@Slf4j
public class TokenProvider {
    private final Algorithm hmac512;
    private final JWTVerifier verifier;
    private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(600);

    public TokenProvider(@Value("${jwt.secret}") String secret) {
        this.hmac512 = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(this.hmac512).build();
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis())))
                .sign(this.hmac512);
    }

    public String validateTokenAndGetUsername(String token){
        try {
            return verifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationEx) {
            log.info("token invalid: {}", verificationEx.getMessage());
            return null;
        }
    }
}
