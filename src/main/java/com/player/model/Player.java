package com.player.model;


import com.player.dto.data.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.*;
import javax.script.ScriptEngine;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "player_roles")
    private Set<Role> roles;

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Ranking ranking;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Column(name = "failed_count")
    private int failedCount;

    @NotNull
    @Column(name = "success_count")
    private int successCount;

    public void buildPoints(final boolean success) {
        if (success) {
            successCount += 1;
        } else {
            failedCount += 1;
        }
    }

    public GameStatus build(final Player player) {
        int total = player.getSuccessCount() + player.getFailedCount();
        System.out.println("total: " + total);
        double percentSuccess = player.getSuccessCount() / 100;
        double points = total * percentSuccess;
        return player.getFailedCount() == 3 ?
                create("You lose, starting again!", points, Status.NOT_PLAYING) :
                player.getSuccessCount() > 3 ?
                        create("You win!", points, Status.NOT_PLAYING) :
                        create("Congratulations, continue!", points);
    }

    private GameStatus create(final String message,
                              final double points,
                              final Status status) {
        this.status = status;
        this.ranking.reset();
        return new GameStatus(message, points);
    }

    private GameStatus create(final String message,
                              final double points) {
        return new GameStatus(message, points);
    }

    public String getFullName() {
        return this.firstName.concat(" ").concat(this.lastName);
    }

    public void buildStatus(final Status status) {
        this.status = status;
    }

    public boolean isWinner() {
        return Status.WINNER.equals(this.status);
    }

    public boolean isLoser() {
        return Status.LOSER.equals(this.status);
    }

    public boolean isPlaying() {
        return Status.PLAYING.equals(this.status);
    }

    public boolean isNotPlaying() {
        return Status.NOT_PLAYING.equals(this.status);
    }

    public Ranking updateRanking() {
        this.ranking.update();
        return this.ranking;
    }
}
