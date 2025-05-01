package com.example.demo.entity;

import com.example.demo.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity//엔티티를 제공한다.
@Setter
@Getter
@Table(name="member_table")//DB에 테이블이 생성될때의 이름 지정
public class MemberEntity {
    @Id//pk지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment 를 지정하는 명령어
    private  Long id;

    @Column(unique = true)//일반 컬럼 지정 unique제약 조건 지정
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private  String memberName;

    //엔티티 객체를 객체로 만들어서 호출하는 것이 아니라 정적 메소드로 사용하는 방식
    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }

}
