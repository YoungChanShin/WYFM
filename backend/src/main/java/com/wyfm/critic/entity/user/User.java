package com.wyfm.critic.entity.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String oauth2Id;

    private String email;

    private String name;

    private String nickName;

    private String description;

    @Column(nullable = false)
    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Builder
    public User(String email, String name, String picture, AuthProvider authProvider) {
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.role = Role.USER;
        this.authProvider = authProvider;
    }

    public static User create(String email, String name, String picture, AuthProvider authProvider) {
        return User.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .authProvider(authProvider)
                .build();
    }

    public void updateDescription(String description) {
        this.description = description;
    }

}
