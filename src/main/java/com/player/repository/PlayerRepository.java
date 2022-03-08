package com.player.repository;

import com.player.model.Player;
import com.player.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Optional<Player> findByEmail(String email);

    Boolean existsByEmail(String email);

    List<Player> findByRanking(Ranking ranking);
}
