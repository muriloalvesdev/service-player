package com.player.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.player.model.Player;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class RankingDto {
    @JsonProperty("full_name")
    private String fullName;

    private double points;

    public static RankingDto build(final Player player) {
        int total = player.getSuccessCount() + player.getFailedCount();
        double percentSuccess = player.getSuccessCount() / 100;
        double points = total * percentSuccess;
        return new RankingDto(player.getFullName(), points);
    }
}
