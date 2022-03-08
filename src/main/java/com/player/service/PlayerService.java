package com.player.service;

import com.player.model.Player;
import com.player.model.Ranking;

import java.util.List;

public interface PlayerService {

    List<Player> findByRanking(Ranking ranking);

    List<Object> playing(final String email);

    Object match(String email, String title, List<Object> movies);

    List<Object> start(String email);
}
