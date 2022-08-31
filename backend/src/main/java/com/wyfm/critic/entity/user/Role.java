package com.wyfm.critic.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "user"),
    ADMIN("ROLE_ADMIN", "admin");

    private final String role;
    private final String name;
}
