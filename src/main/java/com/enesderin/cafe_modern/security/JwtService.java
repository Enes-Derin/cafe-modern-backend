package com.enesderin.cafe_modern.security;

import com.enesderin.cafe_modern.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private JwtProperties jwtProperties;

    public String createToken(UserDetails user){
        return Jwts.builder()
                .claim("authorities" , user.getAuthorities())
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .setIssuedAt(new Date())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    public String getUsernameByToken(String token) {
        return parseToken(token).getSubject();
    }

    public Boolean validateToken(String token) {
        Claims claims = parseToken(token);
        Date expiration = claims.getExpiration();
        return !expiration.before(new Date());
    }

    public SecretKey getKey(){
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }
}
