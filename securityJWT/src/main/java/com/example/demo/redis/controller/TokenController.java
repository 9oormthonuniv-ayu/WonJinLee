package com.example.demo.redis.controller;

import com.example.demo.jwt.JWTUtil;
import com.example.demo.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController//해당 클래스가 REST API 컨트롤러임을 명시 (JSON 응답 전용)
@RequiredArgsConstructor//	final로 선언된 jwtUtil, redisService를 자동 주입
@RequestMapping("/api/token")//이 컨트롤러의 모든 API는 /api/token/ 하위 경로로 시작
public class TokenController {
    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> body){
        String refreshToken = body.get("refreshToken");

    // Refresh Token 유효성 확인
        // 유효성 검증: null, 만료, 파싱 오류 처리
        if (refreshToken == null) {
            return ResponseEntity.status(400).body("Refresh Token이 누락되었습니다.");
        }

        try {
            if (jwtUtil.isExpired(refreshToken)) {
                return ResponseEntity.status(401).body("Refresh Token이 만료되었습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Refresh Token 파싱 오류: " + e.getMessage());
        }

    // 사용자 email 추출
    String email = jwtUtil.getUsername(refreshToken);

    // Redis에서 저장된 Refresh Token 가져오기
    String savedToken = redisService.getRefreshToken(email);


        if (savedToken == null) {
            return ResponseEntity.status(401).body("Redis에 해당 Refresh Token이 존재하지 않습니다.");
        }

        if (!refreshToken.equals(savedToken)) {
            return ResponseEntity.status(401).body("Refresh Token이 Redis와 일치하지 않습니다.");
        }

        // 새로운 Access Token 발급
    String role = jwtUtil.getRole(refreshToken);
    String newAccessToken = jwtUtil.createAccessToken(email, role);

    // 응답
    Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);

        return ResponseEntity.ok(response);
}
}
