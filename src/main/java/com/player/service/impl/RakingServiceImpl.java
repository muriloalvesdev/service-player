package com.player.service.impl;

import com.player.dto.RankingDto;
import com.player.model.Player;
import com.player.repository.RankingRepository;
import com.player.service.PlayerService;
import com.player.service.RankingService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Service
class RakingServiceImpl implements RankingService {
    private final RankingRepository rankingRepository;
    private final PlayerService playerService;

    public List<RankingDto> find(final Pageable pageable) {
        return this.rankingRepository.findAll(pageable).stream()
                .collect(Collectors.toList())
                .stream()
                .map(ranking -> this.playerService.findByRanking(ranking))
                .map(player -> RankingDto.build((Player) player))
                .collect(Collectors.toList());

    }

}
