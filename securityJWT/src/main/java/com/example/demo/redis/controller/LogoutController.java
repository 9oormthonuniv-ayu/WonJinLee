package com.example.demo.redis.controller;

import com.example.demo.jwt.JWTUtil;
import com.example.demo.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LogoutController {
    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    //ttl사용하기 위해 선언 주입
    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {

        // 1. 헤더 검증
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Authorization 헤더가 없습니다.");
        }

        // 2. 토큰 추출
        String accessToken = authHeader.substring(7); // "Bearer " 이후

        // 3. 토큰 만료 여부 확인
        if (jwtUtil.isExpired(accessToken)) {
            return ResponseEntity.status(401).body("Access Token이 만료되었습니다.");
        }

        // 4. 사용자 식별
        String email = jwtUtil.getUsername(accessToken);

        // 5. Access Token 블랙리스트 등록 (남은 TTL 계산 후 저장)
        long ttlMillis = jwtUtil.getRemainingTime(accessToken);
        Duration ttl = Duration.ofMillis(ttlMillis);
        redisTemplate.opsForValue().set("blacklist:" + accessToken, "logout", ttl);


        // 6. Redis에서 Refresh Token 삭제
        redisService.deleteRefreshToken(email);

        return ResponseEntity.ok("로그아웃 성공");
    }
}
