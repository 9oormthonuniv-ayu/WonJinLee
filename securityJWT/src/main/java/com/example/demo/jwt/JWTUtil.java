package com.example.demo.jwt;


import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//0.12.3버전 사용
@Component//Component로 등록되어 관리
public class JWTUtil {
/* 사용자가 로그인 후에 발급 받을 토큰을 생성 혹은 발급 받을 곳
* 토큰의 저장될 정보(urername, role, 생성일, 만료일)
* 구현 메소드
* 1. JWT 생성자
* 2. username 확인 메소드
* 3. role 확인 메소드
* 4.  만료일 확인 메소드
* */
    //secret을 객체 타입으로 암호화 하여 저장하기 위한 클래스
    private SecretKey secretKey;

    // Access Token: 15분 (1000ms * 60s * 15m)
    private static final long ACCESS_EXP = 1000L * 60 * 15;

    // Refresh Token: 7일 (1000ms * 60s * 60m * 24h * 7d)
    private static final long REFRESH_EXP = 1000L * 60 * 60 * 24 * 7;



    //Value을 통해 application.properties에 설정한 특정 값을 읽어 온다.(String secret은 값을 받을 변수)
    public JWTUtil(@Value("${spring.jwt.secret}")String secret){
        //secret을 객체로 만들면서 암호화 하는 과정이다.
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // Access Token 생성 전용
    public String createAccessToken(String username, String role) {
        return createJwt(username, role, ACCESS_EXP);
    }

    // Refresh Token 생성 전용 (role은 넣지 않음)
    public String createRefreshToken(String username) {
        return createJwt(username, null, REFRESH_EXP);
    }

    //Username 검증
    /*
     *Jwts.parser(): 암호화가 된것을 검증하겠다.
     *verifyWith: 토큰이 우리 서버에서 생성되었나요? 우리 키랑 맞나요?
     * parseSignedClaims 확인을 통해 특정한 값을 받아온다. 이때 get("username이라면 username이라는 키를 갖고 있다.", String.class 키 형태 지정);
     */
    public String getUsername(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }
    //Role검증
    public String getRole(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload().get("role", String.class);
    }
    /*
     *isExpired 유효기간이 지났는지 검증
     * getExpiration().before(new Date());를 통해 내부의 현재 시간 값 받기
    * */
    public Boolean isExpired(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    //로그인 성공했을 때에 토큰 생성 후 응답
    /* 토큰이 String이기 때문에 String
    Long expiredMs: 토큰이 살아있을 시간
    Jwts.builder()을 통해 토큰 만들기
     * */
    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)//토큰으 특정 값 입력
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))// 발행 시간 넣기
                .expiration(new Date(System.currentTimeMillis() + expiredMs))//만료시간
                .signWith(secretKey)//암호화 진행
                .compact();//토큰 반환
    }
}
