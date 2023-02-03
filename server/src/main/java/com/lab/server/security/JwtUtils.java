package com.lab.server.security;

import ch.qos.logback.classic.Logger;
import com.lab.server.implementations.UserDetailsImp;
import io.jsonwebtoken.*;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

//Jwt service (get user, generate new, etc)

@Component
public class JwtUtils {
    Logger logger = (Logger) LoggerFactory.getLogger(JwtUtils.class);

    private final String jwtSecret = "secret";

    private final int jwtExpirationMs = 86400000;


    public String generateJwtToken(Authentication authentication) {

        UserDetailsImp userPrincipal = (UserDetailsImp) authentication.getPrincipal();

        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}
