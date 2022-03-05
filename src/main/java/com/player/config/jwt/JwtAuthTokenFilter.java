package com.player.config.jwt;


import com.player.service.impl.PlayerDetailsServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtPlayer jwtPlayer;

    @Autowired
    private PlayerDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtBlacklist blacklist;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {
        final String jwt = getJwt(request);
        if (jwt.equals("undefined")) {
            filterChain(request, response, filterChain);
            return;
        }
        if (!blacklist.check(jwt)) {
            if (StringUtils.isNotBlank(jwt) && jwtPlayer.validateJwtToken(jwt)) {
                final String username = jwtPlayer.getUserNameFromJwtToken(jwt);

                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                final UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain(request, response, filterChain);
        } else {
            response.reset();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acesso não permitido");
            return;
        }
    }

    private void filterChain(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(request, response);
    }

    private String getJwt(final HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        return (nonNull(authHeader) && authHeader.startsWith("Bearer "))
                ? authHeader.replace("Bearer ", "")
                : "";
    }
}
