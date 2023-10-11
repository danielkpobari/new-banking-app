package com.saanacode.bankofdaniel.security;


import com.saanacode.bankofdaniel.entity.Admin;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.saanacode.bankofdaniel.entity.GeneralConstants.*;


@Service
public class JwtService {

    private static final Logger logger = LogManager.getLogger(JwtService.class);

    @Value("${jwt.token.secret}")
    private String SECRET;

    public String generateJwtToken(Admin user) {
        return buildJwt(new HashMap<>(), user);
    }

    public String buildJwt(Map<String, Object> claims,
                           UserDetails userDetails) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String retrieveUsername(String token) {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("sub");
    }

    public Date retrieveExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        try{
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            logger.error("Invalid JWT token");
            throw ex;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = retrieveUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return retrieveExpiration(token).before(new Date());
    }

    public Optional<String> retrieveJWT(HttpServletRequest request) {
        if (hasBearerToken(request)) {
            return Optional.of(retrieveToken(request).substring(7));
        }
        return Optional.empty();
    }

    public boolean hasBearerToken(HttpServletRequest request) {
        String token = retrieveToken(request);
        return StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX);
    }

    private String retrieveToken(HttpServletRequest request) {
        return request.getHeader(HEADER_STRING);
    }
}
