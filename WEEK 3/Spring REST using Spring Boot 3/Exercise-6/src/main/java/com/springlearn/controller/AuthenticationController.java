package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

// AuthenticationController handles Step 1 of JWT flow:
// Client sends credentials → we decode them → we return a JWT token
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    // Inject JwtUtil to generate the token
    @Autowired
    private JwtUtil jwtUtil;

    // -------------------------------------------------------
    // Step 1: Create the authentication controller endpoint
    // URL: /authenticate
    // The curl command sends: curl -u user:pwd http://localhost:8090/authenticate
    // The -u flag tells curl to send HTTP Basic Auth header:
    // Authorization: Basic dXNlcjpwd2Q=
    // (which is Base64 encoding of "user:pwd")
    // -------------------------------------------------------
    @RequestMapping("/authenticate")
    public Map<String, String> authenticate(
            @RequestHeader("Authorization") String authHeader) {

        LOGGER.debug("Start - authenticate");
        LOGGER.debug("Authorization header received: {}", authHeader);

        // -------------------------------------------------------
        // Step 2: Read Authorization header and decode username/password
        // The header format is: "Basic <base64encodedCredentials>"
        // Example: "Basic dXNlcjpwd2Q="
        // We strip "Basic " prefix, then Base64-decode the rest
        // to get "user:pwd", then split on ":" to get username and password
        // -------------------------------------------------------
        String base64Credentials = authHeader.substring("Basic ".length());
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodedBytes);           // "user:pwd"
        String[] parts = credentials.split(":", 2);
        String username = parts[0];                              // "user"
        String password = parts[1];                              // "pwd"

        LOGGER.debug("Decoded username: {}", username);
        // Never log passwords in real apps - only doing this for learning/demo
        LOGGER.debug("Decoded password: {}", password);

        // -------------------------------------------------------
        // Step 3: Generate token based on the retrieved username
        // In a real application we would also validate the password
        // against a database here. For this exercise, Spring Security
        // has already validated the credentials via HTTP Basic Auth
        // (configured in SecurityConfig) before this method is reached.
        // So we trust the username and directly generate the JWT.
        // -------------------------------------------------------
        String token = jwtUtil.generateToken(username);
        LOGGER.debug("JWT token generated for user: {}", username);

        // Return token as JSON: { "token": "eyJhbGci..." }
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        LOGGER.debug("End - authenticate");
        return response;
    }
}
