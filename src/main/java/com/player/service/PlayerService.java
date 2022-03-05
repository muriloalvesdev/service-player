package com.player.service;

import com.player.specification.resource.SearchPlayer;
import com.player.dto.PlayerDto;

import java.util.UUID;

public interface PlayerService {

    PlayerDto findById(UUID uuid);

    PlayerDto search(SearchPlayer searchPlayer);
}
