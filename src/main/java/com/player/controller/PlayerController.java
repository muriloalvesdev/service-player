package com.player.controller;

import com.player.service.PlayerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RestController
@RequestMapping("game")
class PlayerController {

    private final PlayerService service;

    @PreAuthorize("hasRole('PLAYER')")
    @GetMapping("start/{email}")
    public ResponseEntity<?> start(@PathVariable(name = "email") final String email) {
        return ResponseEntity.ok().body(this.service.start(email));
    }

    @PreAuthorize("hasRole('PLAYER')")
    @GetMapping("playing/{email}")
    public ResponseEntity<?> playing(@PathVariable(name = "email") final String email) {
        return ResponseEntity.ok().body(this.service.playing(email));
    }

    @PreAuthorize("hasRole('PLAYER')")
    @PostMapping("{title}/{email}")
    public ResponseEntity<Object> match(@PathVariable(name = "title") final String title,
                                                     @PathVariable(name = "email") final String email,
                                                     @RequestBody List<Object> movies) {
        return ResponseEntity.ok(this.service.match(email, title, movies));
    }


}
