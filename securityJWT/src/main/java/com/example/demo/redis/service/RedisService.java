package com.example.demo.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7); // 7일 TTL

    //Refresh Token 저장
    public void saveRefreshToken(String email, String refreshToken) {
        redisTemplate.opsForValue().set(getRefreshKey(email), refreshToken, REFRESH_TOKEN_TTL);
    }
    //Refresh Token 조회
    public String getRefreshToken(String email) {
        Object token = redisTemplate.opsForValue().get(getRefreshKey(email));
        return token != null ? token.toString() : null;
    }

    //Refresh Token 삭제
    public void deleteRefreshToken(String email) {
        redisTemplate.delete(getRefreshKey(email));
    }
    //키 네이밍 규칙
    private String getRefreshKey(String email) {
        return "refresh:" + email;
    }
}

