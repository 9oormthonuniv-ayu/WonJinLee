package com.example.demo.jwt;


import com.example.demo.user.dto.CustomUserDetails;
import com.example.demo.user.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//발급 받은 토큰을 검증하는 필터 임시로 세션을 사용하기 때문에 사용자를 기억하지 못한다.
//OncePerRequestFilter를 상속 받는 이유 요청에 따라 한번만 실행하기 위해
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil=jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request에서 JWTUtil를 뽑아야 한다.
        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");
        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);//필터 체인에서 현재 필터 종료 후 다음 필터로 넘긴다.

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        System.out.println("authorization now");
        //Bearer 부분 제거 후 순수 토큰(뒤에 부분)만 획득 " "로 구분
        String token = authorization.split(" ")[1];

        //jwtUtil.isExpired를 통해 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰 확인 완료 일시적인 세션을 만들어서 저장하기

        //세션에 저장하기 위해 토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        //userEntity를 생성하여 값 set
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("temppassword");//비밀번호는 jwtUtil에 없음 왜냐 매번 요청마다 비번 주는것은 비효율
        userEntity.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증 토큰 생성
        //customUserDetails의 정보를 담고 있고 credentials(비밀번호 자리)는 null 비밀번호는 생략 (이미 인증이 끝난 상황이므로 필요 없음)
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
