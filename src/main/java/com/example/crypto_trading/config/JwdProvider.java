package com.example.crypto_trading.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwdProvider {
    private static SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        String jwt = Jwts.builder().issuedAt(new Date()).expiration(new Date(new Date().getTime() + 24 * 60 * 60 * 1000))
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(secretKey)
                .compact();
        return jwt;
    }

    public static String getEmailFromToken(String token) {
        token =token.substring(7);
        Claims claims =  Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload();
        String email = String.valueOf(claims.get("email"));
        return email;
    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auth.add(authority.getAuthority());
        }
        return String.join(",", auth);
    }
}
