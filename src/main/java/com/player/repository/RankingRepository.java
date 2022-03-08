package com.player.repository;

import com.player.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RankingRepository extends JpaRepository<Ranking, UUID> {
}
