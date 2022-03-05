package com.player.controller;

import com.player.service.PlayerService;
import com.player.dto.PlayerDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RestController("player")
class PlayerController {

    private final PlayerService service;

    @PreAuthorize("hasRole('PLAYER')")
    @GetMapping("/{uuid}")
    public ResponseEntity<PlayerDto> find(@PathVariable final UUID uuid) {
        return ResponseEntity.ok(this.service.findById(uuid));
    }

}
