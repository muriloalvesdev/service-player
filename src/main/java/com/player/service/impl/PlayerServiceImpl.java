package com.player.service.impl;

import com.player.service.PlayerService;
import com.player.specification.resource.SearchPlayer;
import com.player.dto.PlayerDto;
import com.player.exception.PlayerNotFoundException;
import com.player.repository.PlayerRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PlayerServiceImpl implements PlayerService {
    private static final String MESSAGE_NOT_FOUND = "Player not found!";

    private final PlayerRepository playerRepository;

    public PlayerDto search(SearchPlayer searchPlayer) {
        return null;
    }

    public PlayerDto findById(final UUID uuid) {
        return this.playerRepository.findById(uuid)
                .map(PlayerDto::build)
                .orElseThrow(() -> new PlayerNotFoundException(MESSAGE_NOT_FOUND));
    }

}
