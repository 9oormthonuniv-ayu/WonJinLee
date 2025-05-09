package com.example.demo.jwt;

import com.example.demo.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

//authenticationManager이 친구가 사용자가 입력한 값을 필터로부터 전달 받아서 비교 후 다음 동작을 한다.
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    //AuthenticationManager는 사용자의 아이디/비밀번호를 검증해주는 핵심 컴포넌트이다.
    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;//토큰을 생성하고 검증하는 부분을 사용하기

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil=jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출
        String username= obtainUsername(request);//request.getParmrter("username")과 같은 효과이다.
        String password= obtainPassword(request);

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달 이후에 실제 인증 시도
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    /*해야 하는 일 동작
    * - 사용자 정보를 authentication 객체에서 꺼내기
    * - JWT 토큰 생성
    * - 응답에 JWT를 헤더로 포함하거나, 클라이언트에 JSON 응답으로 전달
    * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        //특정 유저 확인
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //authentication(사용자 입력)부터 username 추출
        String username = customUserDetails.getUsername();
        //authentication(사용자 입력)부터 Role값 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        //추출한 이름과 role를 통해 토큰 만들기
        String token= jwtUtil.createJwt(username, role, 60*60*10L);

        //완성한 토큰을 해더에 담아서 전달 담을때의 Authorization라는 키값으로 담음
        //인증 방식(Scheme)은 Bearer이다. 띄어쓰기 필수"Bearer " + token
        response.addHeader("Authorization", "Bearer " + token);
    }
    //로그인 실패시 실행하는 메소드
    /*
    보통 401 에러 응답을 보내거나, 에러 메시지를 JSON으로 반환
    * */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        //응답 실패시 401 응답 코드 변환
        response.setStatus(401);
    }

}
