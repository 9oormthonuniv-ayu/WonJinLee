package com.example.demo.redis.controller;

import com.example.demo.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisTestController {

    private final RedisService redisService;

//    @GetMapping("/redis/test")
//    public String testRedis() {
//        redisService.setValue("hello", "redis 연결 완료!");
//        return redisService.getValue("hello").toString();
//    }
}
