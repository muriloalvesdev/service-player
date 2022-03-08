package com.player.service;

import com.player.dto.RankingDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RankingService {
    List<RankingDto> find(final Pageable pageable);
}
