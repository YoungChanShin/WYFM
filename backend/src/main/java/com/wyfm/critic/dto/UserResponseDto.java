package com.wyfm.critic.dto;

import com.wyfm.critic.entity.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private String email;
    private String name;
    private String picture;

    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(user.getEmail(), user.getName(), user.getPicture());
    }
}
