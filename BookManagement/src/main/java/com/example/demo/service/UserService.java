package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    public List<UserDTO> userFindAll (){
        List<UserEntity> userEntityList= userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity:userEntityList){
            userDTOList.add(UserDTO.toUserDTO(userEntity));
        }
        return userDTOList;
    }
    public UserDTO userFindName(String  userName){
        Optional<UserEntity> optionalUserEntityName = userRepository.findByUserEmail(userName);
        if (optionalUserEntityName.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntityName.get());
        }else return null;
    }

    public UserDTO userFindEmail(String  userEmail){
        Optional<UserEntity> optionalUserEntityEmail = userRepository.findByUserEmail(userEmail);
        if (optionalUserEntityEmail.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntityEmail.get());
        }else return null;
    }

    public UserDTO updateForm(String userEmail){
        Optional<UserEntity> optionalUserEntityEmail = userRepository.findByUserEmail(userEmail);
        if(optionalUserEntityEmail.isPresent()){
            return UserDTO.toUserDTO(optionalUserEntityEmail.get());
        }
        else return null;
    }
    public void userUpdate(UserDTO userDTO){
        userRepository.save(UserEntity.toUpdateUserEntity(userDTO));
    }
    public void userDelete(long userId){
        userRepository.deleteById(userId);
    }

    public void deleteSelectedUsers(List<Long> userIds) {
        for (Long userId : userIds) {
            userRepository.deleteById(userId);
        }
    }


}
