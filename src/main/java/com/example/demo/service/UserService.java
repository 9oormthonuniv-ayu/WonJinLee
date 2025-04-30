package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(UserDTO userDTO){
        UserEntity userEntity=UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
    }

    public UserDTO login(UserDTO userDTO) {
        Optional<UserEntity> byUserEmail = userRepository.findByUserEmail(userDTO.getUserEmail());
        if(byUserEmail.isPresent()){
            UserEntity userEntity = byUserEmail.get();
            if(userEntity.getUserPassword().equals(userDTO.getUserPassword())){
                UserDTO dto = UserDTO.toUserDTO(userEntity);
                return dto;
            }else {
                return null;//비번 틀림
            }
        }
        return null;//이메일 없음
    }
}
