package com.player.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@Entity
@Table(name = "rankng")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count_quiz")
    private double countQuiz;

    public Ranking update() {
        this.countQuiz += 1;
        return this;
    }

    public void reset() {
        this.countQuiz = 0;
    }
}
