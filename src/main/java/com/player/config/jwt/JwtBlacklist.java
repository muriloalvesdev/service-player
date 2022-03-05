package com.player.config.jwt;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@Scope("singleton")
public class JwtBlacklist {
    private static Map<String, String> blacklist = new ConcurrentHashMap<>();
    private String jwtSecret;

    public synchronized void add(String token) {
        if (!blacklist.containsKey(token)) {
            blacklist.putIfAbsent(token, String.valueOf(System.currentTimeMillis()));
        } else {
            log.info("Token has already been invalidated! Token: [{}] ", token);
        }
    }

    public boolean check(String token) {
        return !StringUtils.isEmpty(token) && blacklist.containsKey(token);
    }

    public synchronized void cleanBlacklist() {
        for (String token : blacklist.keySet()) {
            try {
                getDateExpirationToken(token);
            } catch (ExpiredJwtException e) {
                blacklist.remove(token);
            }
        }
    }

    private LocalDateTime getDateExpirationToken(final String token) {
        return Jwts.parser()
                .setSigningKey(this.jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
