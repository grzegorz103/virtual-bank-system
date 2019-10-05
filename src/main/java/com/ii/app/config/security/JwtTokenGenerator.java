package com.ii.app.config.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenGenerator {

    private JwtTokenGenerator() {
    }

    @SuppressWarnings("deprecation")
    public String generate(String username, List<String> roles) {
        byte[] apiKeySecretBytes = SecurityConstants.JWT_SECRET.getBytes();
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
            .claim("rol", roles)
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TTL_TOKEN))
            .signWith(SignatureAlgorithm.HS512, signingKey)
            .compact();
    }
}
