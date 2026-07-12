package com.cognizant.springlearn.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// JwtUtil is a Spring-managed component responsible for
// generating JWT tokens. It is injected into AuthenticationController.
@Component
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    // Read secret key from application.properties
    @Value("${jwt.secret}")
    private String secret;

    // Read token validity (milliseconds) from application.properties
    @Value("${jwt.validity}")
    private long validity;

    // Generate a signed JWT token for the given username.
    // A JWT has three parts:  HEADER . PAYLOAD . SIGNATURE
    //
    // HEADER: { "alg": "HS256" }
    // PAYLOAD (claims):
    //   sub  = subject (username)
    //   iat  = issued at (current timestamp)
    //   exp  = expiry (current + validity ms)
    // SIGNATURE: HMACSHA256(base64(header) + "." + base64(payload), secret)
    //
    // All three parts are Base64-encoded and joined with dots.
    public String generateToken(String username) {
        LOGGER.debug("Generating JWT token for user: {}", username);

        Map<String, Object> claims = new HashMap<>();

        // Derive a secure signing key from the secret string
        Key signingKey = Keys.hmacShaKeyFor(secret.getBytes());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)                              // "sub" claim
                .setIssuedAt(new Date(System.currentTimeMillis())) // "iat" claim
                .setExpiration(new Date(System.currentTimeMillis() + validity)) // "exp" claim
                .signWith(signingKey, SignatureAlgorithm.HS256)    // sign with HMAC-SHA256
                .compact();                                        // build the token string

        LOGGER.debug("Token generated successfully");
        return token;
    }
}
