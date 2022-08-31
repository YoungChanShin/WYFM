package com.wyfm.critic.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyfm.critic.entity.user.AuthProvider;
import com.wyfm.critic.entity.user.User;
import com.wyfm.critic.model.oauth.KakaoProfile;
import com.wyfm.critic.model.oauth.OauthToken;
import com.wyfm.critic.repository.UserRepository;
import com.wyfm.critic.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public OauthToken getAccessToken(String code) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "");
        params.add("redirect_uri", "");
        params.add("code", code);
        params.add("client_secret", "");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
    }

    public String loginKakaoUserAndGetToken(String accessToken) throws JsonProcessingException {
        KakaoProfile kakaoProfile = findKakaoProfile(accessToken);

        User user = userRepository.findByEmail(kakaoProfile.getKakaoAccount().getEmail())
                .orElse(saveUser(kakaoProfile));

        return createToken(user);
    }

    private String createToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", user.getId())
                .withClaim("nickname", user.getName())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    private User saveUser(KakaoProfile kakaoProfile) {
        User user = User.create(
                kakaoProfile.getKakaoAccount().getEmail(),
                kakaoProfile.getKakaoAccount().getProfile().getNickname(),
                kakaoProfile.getKakaoAccount().getProfile().getProfileImageUrl(),
                AuthProvider.KAKAO
        );
        return userRepository.save(user);
    }

    private KakaoProfile findKakaoProfile(String token) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        ResponseEntity<String> kakaoProfileResponse = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
    }

    public User getUser(HttpServletRequest request) {
        Long userCode = (Long) request.getAttribute("userCode");

        return userRepository.findById(userCode)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다."));
    }
}
