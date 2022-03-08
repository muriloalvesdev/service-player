package com.player.service.component;

import java.util.List;

public interface MoviesBattleComponent {
    Object match(String email, String title, List<Object> movies);

    List<Object> start(String email);

    List<Object> playing(final String email);
}
