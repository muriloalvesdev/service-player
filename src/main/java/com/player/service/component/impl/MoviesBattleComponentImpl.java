package com.player.service.component.impl;

import com.player.dto.data.GameStatus;
import com.player.dto.data.ResponseMoviesMatch;
import com.player.exception.EmailNotFoundException;
import com.player.exception.InvalidGameException;
import com.player.model.Player;
import com.player.model.Ranking;
import com.player.model.Status;
import com.player.repository.PlayerRepository;
import com.player.repository.RankingRepository;
import com.player.service.component.MoviesBattleComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.lang.String.valueOf;

@Slf4j
@Component
class MoviesBattleComponentImpl implements MoviesBattleComponent {

    private static final Map<String, Integer> keyRequest = new HashMap<>();

    private final RestTemplate restTemplate;
    private final String key;
    private final String urlMoviesBattle;
    private final PlayerRepository playerRepository;
    private final RankingRepository rankingRepository;

    MoviesBattleComponentImpl(final RestTemplate restTemplate,
                              @Value("${key.movies.battle}") final String key,
                              @Value("${url.movies.battle}") final String urlMoviesBattle,
                              final PlayerRepository playerRepository,
                              final RankingRepository rankingRepository) {
        this.restTemplate = restTemplate;
        this.key = key;
        this.urlMoviesBattle = urlMoviesBattle;
        this.playerRepository = playerRepository;
        this.rankingRepository = rankingRepository;
    }

    public List<Object> start(final String email) {
        Player player = getPlayer(email);
        if (player.isNotPlaying()) {
            player.buildStatus(Status.PLAYING);
            updatePlayer(player);
            return getNextMovies();
        }
        throw new InvalidGameException("You cannot start a new game without completing the previous one.");
    }

    public List<Object> playing(final String email) {
        final Player player = getPlayer(email);
        if (player.isPlaying()) {

            return getNextMovies();
        }
        throw new InvalidGameException("You not is playing, start game.");
    }

    public Object match(final String email,
                        final String title,
                        final List<Object> movies) {
        Player player = getPlayer(email);
        if (player.isPlaying()) {
            final String url = "http://"
                    .concat(this.urlMoviesBattle)
                    .concat(title)
                    .concat("/")
                    .concat(this.key);

            final ResponseMoviesMatch responseMoviesMatch =
                    this.restTemplate.postForObject(url, movies, ResponseMoviesMatch.class);

            player.buildPoints(responseMoviesMatch.isMatch());
            updateRanking(player.updateRanking());
            updatePlayer(player);
            return player.build(player);
        }
        throw new InvalidGameException("You cannot attempt to continue a game without starting it.");
    }

    private List<Object> getNextMovies() {
        if (keyRequest.containsKey("page")) {
            final Integer page = keyRequest.get("page");
            keyRequest.put("page", page + 1);
            return getNewMovies(page + 1);
        }
        keyRequest.put("page", 0);
        return getNewMovies(0);
    }

    private void updatePlayer(final Player player) {
        this.playerRepository.saveAndFlush(player);
    }

    public void updateRanking(final Ranking ranking) {
        this.rankingRepository.saveAndFlush(ranking);
    }

    private Player getPlayer(final String email) {
        return this.playerRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(format("email [%s] not found", email)));
    }

    private List<Object> getNewMovies(final Integer page) {
        final String url = "http://".concat(this.urlMoviesBattle)
                .concat(this.key)
                .concat("?size=2")
                .concat("&page=")
                .concat(valueOf(page));
        return this.restTemplate.getForObject(url, List.class);
    }
}
