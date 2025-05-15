package com.example.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name = "user_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//id 중복 제거 및 자동 관리
    private int id;

    private String username;
    private String password;

    private String role;
}