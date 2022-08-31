package com.wyfm.critic.model.oauth;

import lombok.Data;

@Data
public class OauthToken {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private int expiresIn;
    private String scope;
    private int refreshTokenExpiresIn;
}
