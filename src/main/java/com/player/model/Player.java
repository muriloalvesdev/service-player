package com.player.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Builder
@Entity
@Table(name = "player", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
public class Player extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @NotNull
    @Column(name = "first_name")
    protected String firstName;

    @NotNull
    @Column(name = "last_name")
    protected String lastName;

    @Email
    @Column(name = "email")
    protected String email;

    @NotNull
    @Column(name = "password")
    protected String password;

    @NotNull
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "player_roles")
    protected Set<Role> roles;

}
