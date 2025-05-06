package com.example.demo.user.entity;

import com.example.demo.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity//엔티티를 제공한다.
@Setter
@Getter
@Table(name="user_table")//DB에 테이블이 생성될때의 이름 지정
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {



        @Id//pk지정
        @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment 를 지정하는 명령어
        private  Long userId;

        @Column(unique = true)//일반 컬럼 지정 unique제약 조건 지정
        private String userEmail;

        @Column
        private String userPassword;

        @Column
        private  String userName;

        @Column
        private String userPhoneNumber;

        @Column(columnDefinition = "BOOLEAN DEFAULT false")
        private boolean isAdmin;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime userCreated;//이런식으로 다른 곳에서도 사용하는 컬럼은 인터페이스에 저장하고 사용하면 좋다.


    //엔티티 객체를 객체로 만들어서 호출하는 것이 아니라 정적 메소드로 사용하는 방식
        public static UserEntity toUserEntity(UserDTO userDTO){
            UserEntity userEntity = new UserEntity();
            userEntity.setUserEmail(userDTO.getUserEmail());
            userEntity.setUserPassword(userDTO.getUserPassword());
            userEntity.setUserName(userDTO.getUserName());
            userEntity.setUserPhoneNumber(userDTO.getUserPhoneNumber());
            // userCreated는 DB가 자동 입력하기 때문에 넣지 않는다
            userEntity.setAdmin(userDTO.isAdmin());
            return userEntity;
        }

        public static UserEntity toUpdateUserEntity(UserDTO userDTO){
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(userDTO.getUserId());
            userEntity.setUserEmail(userDTO.getUserEmail());
            userEntity.setUserPassword(userDTO.getUserPassword());
            userEntity.setUserName(userDTO.getUserName());
            userEntity.setUserPhoneNumber(userDTO.getUserPhoneNumber());
            userEntity.setAdmin(userDTO.isAdmin());
            return userEntity;
        }
}
