package com.example.demo.user.dto;

import com.example.demo.user.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor //이번 생성자
@AllArgsConstructor//아래의 모든 필드를 매개변수로 하는 생성자를 만들어준다.
@ToString//tosting메소드를 자동으로 만들어줌
public class UserDTO {
    private Long userId;
    private String userEmail;
    private  String userPassword;
    private  String userName;
    private String userPhoneNumber;
    private LocalDateTime userCreated;
    private boolean isAdmin;
    
    //엔티티를 DTO로 변경
    public static UserDTO toUserDTO(UserEntity userEntity){
        UserDTO userDTO= new UserDTO();
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setUserEmail(userEntity.getUserEmail());
        userDTO.setUserPassword(userEntity.getUserPassword());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setUserPhoneNumber(userEntity.getUserPhoneNumber());
        userDTO.setUserCreated(userEntity.getUserCreated());
        userDTO.setAdmin(userEntity.isAdmin());
        return userDTO;
    }


}
