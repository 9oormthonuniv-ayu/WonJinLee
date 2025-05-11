package com.example.demo.member.repository;

import com.example.demo.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//어떤 엔티티를 다룰것인가"MemberEntity" 옆에 Long은 같이 있는 엔티티의 PK값이 어떠한 형태인지
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    //이메일로 회원정보 조회
    // 하고 싶은 동작-> select * from member_table where member_email=?
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
