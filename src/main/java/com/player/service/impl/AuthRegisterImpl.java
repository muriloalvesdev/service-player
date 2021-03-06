package com.player.service.impl;

import com.player.config.jwt.JwtPlayer;
import com.player.dto.LoginDTO;
import com.player.dto.RegisterDTO;
import com.player.exception.ExistingEmailException;
import com.player.exception.RoleNotFoundException;
import com.player.model.*;
import com.player.repository.PlayerRepository;
import com.player.repository.RankingRepository;
import com.player.repository.RoleRepository;
import com.player.service.AuthRegister;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Service
class AuthRegisterImpl implements AuthRegister {

    private static final String EMAIL_IS_ALREADY = "Fail -> Email is already in use!";
    private static final String ROLE_NOT_FOUND = "Role not found!.";

    private final PlayerRepository playerRepository;
    private final RankingRepository rankingRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtPlayer jwtPlayer;
    private final AuthenticationManager authenticationManager;

    public Player registerUser(final RegisterDTO registerData) {
        if (this.playerRepository.existsByEmail(registerData.getEmail())) {
            throw new ExistingEmailException(EMAIL_IS_ALREADY);
        }
        final Role role = this.roleRepository
                .findByName(RoleName.ROLE_PLAYER)
                .orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND));

        final Ranking ranking = this.rankingRepository
                .saveAndFlush(Ranking.builder().countQuiz(0.0).build());


        final Player player = Player.builder()
                .email(registerData.getEmail())
                .firstName(registerData.getFirstName())
                .lastName(registerData.getLastName())
                .roles(Set.of(role))
                .status(Status.NOT_PLAYING)
                .failedCount(0)
                .ranking(ranking)
                .password(this.encoder.encode(registerData.getPassword()))
                .build();

        return this.playerRepository.saveAndFlush(player);
    }

    public AccessToken authenticateUser(final LoginDTO loginDto) {
        final Authentication authentication =
                this.authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new AccessToken(
                this.jwtPlayer.generateJwtToken(authentication));
    }
}
