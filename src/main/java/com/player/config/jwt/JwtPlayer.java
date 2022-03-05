package com.player.config.jwt;

import com.player.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtPlayer {

    @Value("${security.app.jwtSecret}")
    private String jwtSecret;

    @Value("${security.app.jwtExpiration}")
    private int jwtExpiration;

    public String generateJwtToken(final Authentication authentication) {
        final UserDTO userDto = (UserDTO) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDto.getEmail())
                .setIssuedAt(new Date())
                .claim("username", userDto.getName())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(final String token) {
        return this.getUserInformation(token).getSubject();
    }

    public Claims getUserInformation(final String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public boolean validateJwtToken(final String authToken) {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return true;
    }
}
