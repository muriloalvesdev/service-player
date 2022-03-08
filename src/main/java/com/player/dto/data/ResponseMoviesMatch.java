package com.player.dto.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseMoviesMatch {
    private boolean match;

    @Override
    public String toString() {
        return "ResponseMoviesMatch{" +
                "match=" + match +
                '}';
    }
}
