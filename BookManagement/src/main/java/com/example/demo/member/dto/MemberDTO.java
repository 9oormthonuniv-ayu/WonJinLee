package com.example.demo.member.dto;

import com.example.demo.member.entity.MemberEntity;
import lombok.*;

//
@Getter
@Setter
@NoArgsConstructor //이번 생성자
@AllArgsConstructor//아래의 모든 필드를 매개변수로 하는 생성자를 만들어준다.
@ToString//tosting메소드를 자동으로 만들어줌
public class MemberDTO {
    /*
    프라이빗으로 정보를 감춘다.
    롬북을 통해 각각에 대한 get과 set을 어노케이션으로 사용할 수 있다.
     html의 name과 필드의 이름이 동일하다면 알아서 dto객체를 만들어서 set를 자동으로 생성 사용하여
     담아준다.
     */
    private Long id;
    private String memberEmail;
    private  String memberPassword;
    private  String memberName;

    //엔티티를 DTO로 변경
    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO= new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        return memberDTO;
    }
}
