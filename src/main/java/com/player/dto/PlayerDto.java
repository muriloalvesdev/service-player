package com.player.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.player.model.Player;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = PRIVATE)
@Data
public class PlayerDto {

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("email")
    private String email;

    public static PlayerDto build(final Player player) {
        return new PlayerDto(
                player.getFirstName()
                        .concat(" ")
                        .concat(player.getLastName()),
                player.getEmail());
    }
}
