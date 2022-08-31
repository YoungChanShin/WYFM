package com.wyfm.critic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wyfm.critic.dto.UserResponseDto;
import com.wyfm.critic.entity.user.User;
import com.wyfm.critic.model.oauth.OauthToken;
import com.wyfm.critic.security.jwt.JwtProperties;
import com.wyfm.critic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/api/oauth/token")
    public ResponseEntity<String> getLogin(@RequestParam("code") String code) throws JsonProcessingException {
        OauthToken oauthToken = userService.getAccessToken(code);

        String jwtToken = userService.loginKakaoUserAndGetToken(oauthToken.getAccessToken());

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        return ResponseEntity.ok().headers(headers).body("success");
    }

    @GetMapping("/api/oauth/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(HttpServletRequest request) {
        User user = userService.getUser(request);

        return ResponseEntity.ok().body(UserResponseDto.fromEntity(user));
    }
}
