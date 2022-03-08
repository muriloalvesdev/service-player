package com.player.service.impl;

import com.player.model.Player;
import com.player.model.Ranking;
import com.player.repository.PlayerRepository;
import com.player.service.PlayerService;
import com.player.service.component.MoviesBattleComponent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PlayerServiceImpl implements PlayerService {
    private final MoviesBattleComponent moviesBattleComponent;
    private final PlayerRepository repository;
    @Override
    public List<Player> findByRanking(final Ranking ranking) {
        return this.repository.findByRanking(ranking);
    }

    @Override
    public List<Object> playing(final String email) {
        return this.moviesBattleComponent.playing(email);
    }

    @Override
    public Object match(final String email,
                                     final String title,
                                     final List<Object> movies) {
        return this.moviesBattleComponent.match(email, title, movies);
    }

    @Override
    public List<Object> start(final String email) {
        return this.moviesBattleComponent.start(email);
    }

}
