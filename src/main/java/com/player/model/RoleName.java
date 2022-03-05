package com.player.model;

import java.util.Arrays;

public enum RoleName {
    ROLE_PLAYER("player"),
    UNKNOWN("unknown");

    private final String roleName;

    RoleName(final String roleName) {
        this.roleName = roleName;
    }

    public static final RoleName from(final String roleName) {
        return Arrays.asList(RoleName.values())
                .stream()
                .filter(role -> role.getRoleName().equalsIgnoreCase(roleName))
                .findAny()
                .orElse(RoleName.UNKNOWN);
    }

    private String getRoleName() {
        return roleName;
    }
}
