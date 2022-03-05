package com.player.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.player.model.Player;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PACKAGE;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor(access = PACKAGE)
@EqualsAndHashCode
@Getter
@Setter
public class UserDTO implements UserDetails {
    private static final long serialVersionUID = -5745731685321252631L;

    private UUID id;
    private String name;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDTO build(final Player player) {
        final List<GrantedAuthority> authorities =
                player.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList());

        return new UserDTO(
                UUID.randomUUID(),
                player.getFirstName(),
                player.getLastName(),
                player.getEmail(),
                player.getPassword(),
                authorities);
    }

    @Override
    public String getUsername() {
        return lastName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
