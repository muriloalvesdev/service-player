package com.player.controller;

import com.player.dto.RankingDto;
import com.player.service.RankingService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RestController
@RequestMapping("ranking")
class RakingController {

    private final RankingService service;

    @GetMapping
    public ResponseEntity<List<RankingDto>> find(final Pageable pageable) {
        return ResponseEntity.ok(this.service.find(pageable));
    }
}
