package com.example.demo.user.service;

import com.example.demo.user.dto.CustomUserDetails;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //사용자 조회 서비스
public class CustomUserDetailsService implements UserDetailsService {
    //데이터 베이스에 접근
    private  final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

//사용자의 정보를 username을 통해 DB의 UserEntity에서 조회하고 CustomUserDetails로 포장한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserEntity userEntity= userRepository.findByUsername(username);
        if(userEntity!=null){
            return  new CustomUserDetails(userEntity);
        }
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
    }
}
