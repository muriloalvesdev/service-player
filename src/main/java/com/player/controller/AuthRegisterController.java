package com.player.controller;

import com.player.dto.LoginDTO;
import com.player.dto.RegisterDTO;
import com.player.model.AccessToken;
import com.player.service.AuthRegister;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthRegisterController {

    private final AuthRegister authRegister;

    @PostMapping("/login")
    public ResponseEntity<AccessToken> authenticateUser(@Validated @RequestBody LoginDTO loginData) {
        return ResponseEntity.ok().body(this.authRegister.authenticateUser(loginData));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> save(@Validated @RequestBody final RegisterDTO registerData) {
        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/{uuid}")
                                .buildAndExpand(this.authRegister.registerUser(registerData).getUuid())
                                .toUri())
                .build();
    }


}
