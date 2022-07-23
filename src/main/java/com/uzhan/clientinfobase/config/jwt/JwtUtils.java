package com.uzhan.clientinfobase.config.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.jwt.secretKey}")
    private String secretKey;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";
    @Value("${app.jwt.expiredDate}")
    private Long expiredDate;

    public String generateToken(String username) {
        Date currentTime = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentTime)
                .setExpiration(new Date(currentTime.getTime() + expiredDate))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("token expired");
        } catch (MalformedJwtException malformedJwtException) {
            System.err.println("token modified");
        } catch (SignatureException s) {
            System.err.println("incorrect token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            System.err.println("unsupported token");
        } catch (IllegalArgumentException ex) {
            System.err.println("blank token");
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
