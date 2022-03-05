package com.player.specification.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.player.dto.PlayerDto;
import lombok.Data;

@Data
public class SearchPlayer {
    @JsonProperty("player_dto")
    private PlayerDto playerDto;
}
