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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
public class JwtPlayer {

    @Value("${security.app.jwtSecret}")
    private String jwtSecret;

    public String generateJwtToken(final Authentication authentication) {
        final UserDTO userDto = (UserDTO) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDto.getEmail())
                .setIssuedAt(new Date())
                .claim("username", userDto.getName())
                .setExpiration(Date.from(createTimeExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private Instant createTimeExpiration() {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        return issuedAt.plus(30, ChronoUnit.MINUTES);
    }

    public String getUserNameFromJwtToken(final String token) {
        return this.getUserInformation(token).getSubject();
    }

    public Claims getUserInformation(final String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public boolean validateJwtToken(final String authToken) throws ExpiredJwtException {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return true;
    }
}
