package com.example.demo.member.service;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.entity.MemberEntity;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//스프링이 관리하는 객체 스피링 빈으로 등록
@Service
@RequiredArgsConstructor
public class MemberService {
    private  final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        /* Repository의 save메소드 호출(조건 entity객체를 넘겨줘야 한다.)
        필요한 동작
        1. dto -> entity로 변환
        2. repository의 save 메소드 호출
        */
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);//memberRepository.save는 JPA가 제공하는 메소드 때문에 이름을 save로 해야함
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
        1. 회원이 입력한 이메일로 DB에서 조회
        2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 확인
        */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()){
            //조회 결과가 있다.(해당 이메일을 가진 회원정보가 존재)
            MemberEntity memberEntity = byMemberEmail.get();//옵셔널로 감싸진 것을 여는 과정
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                    //memberEntity.getMemberPassword()는 db에 존재하는 비밀번호
                    //dto는 입력받은 비밀번호
                //엔티티(db)에서 받은 내용을 dto로 변경후 넘겨주기
                MemberDTO dto =  MemberDTO.toMemberDTO(memberEntity);
                return dto;
            }else {//비번 틀림
                return null;
            }
        }else {
            //조회 결과가 없다.(해당 이메일을 가진 회원정보가 없다.)
            return null;

        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList =new ArrayList<>();

        //받아온 엔티티리스트를 DTO리스트로 변경
        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntityId = memberRepository.findById(id);
        if (optionalMemberEntityId.isPresent()){//Optional은 절대 null이 아니라 null이랑 비교 불가
            return MemberDTO.toMemberDTO(optionalMemberEntityId.get());
        }
        else {
            return null;
        }

    }


    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }
        else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void delete(long id) {
        memberRepository.deleteById(id);
    }

    public boolean emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if(byMemberEmail.isPresent()){//빈거임?
            return true;//이미 id존재
        }else {
            return false;
        }
    }
}

