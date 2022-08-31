package com.wyfm.critic.security.jwt;

public interface JwtProperties {
    String SECRET = "hahaitisasecret";
    int EXPIRATION_TIME = 864000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
