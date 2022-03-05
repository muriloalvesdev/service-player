package com.player.service.impl;

import com.player.exception.EmailNotFoundException;
import com.player.model.Player;
import com.player.dto.UserDTO;
import com.player.repository.PlayerRepository;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = PRIVATE)
public class PlayerDetailsServiceImpl implements UserDetailsService {

    private static final String MESSAGE_NOT_FOUND = "Player Not Found with -> email: %s";

    private final PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) {

        final Player player = this.playerRepository
                .findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(MESSAGE_NOT_FOUND + email));

        return UserDTO.build(player);
    }
}
