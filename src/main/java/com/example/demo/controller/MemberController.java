package com.example.demo.controller;

import com.example.demo.dto.MemberDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor//memmberService를 필드로 하는 MemberController생성자를 만들어줌
public class MemberController {
    //회원가입 페이지 출력 요청
    private final MemberService memberService;
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    //html -> 컨트롤러 -> 서비스->레포지토리->데이터베이스
    /* 하나 씩 값을 파라미터로 전달 받아 사용
    html코드에서 데이터를 받는 부분 아래 주석 코드 초록색 부분에 이름을 적어준다.
    그러면 어노테이션 부분에서 받은 값을 오른쪽 memberEmail에 저장하는 형식이다.
    @RequestParam("memberEmail") String memberEmail,
    @RequestParam("memberPassword") String memberPassword,
    @RequestParam("memberName") String memberName
    */
    public String save(@ModelAttribute MemberDTO memberDTO)
    {
        System.out.println("MemberController.save성공 수발");
        //파라미터의 값 출력 soutp
        System.out.println("memberDTO = " + memberDTO);
        //생성자 주입을 위해 서비스의 save메소드 생성
        memberService.save(memberDTO);
        return "login";
    }

    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }


    @PostMapping("/member/login")
    public String login (@ModelAttribute MemberDTO memberDTO, HttpSession session){ //HttpSession sessione다른 곳에서도 로그인 유지
        MemberDTO loginResult=memberService.login(memberDTO);
        if(loginResult!=null){
            //로그인 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        }else {
            //로그인 실패
            return "login";
        }
    }

    @GetMapping("/member/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList= memberService.findAll();
        //어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("memberDTOList", memberDTOList);
        return "List";
    }

    @GetMapping("/member/{id}")//PathVariable경로상의 값을 받아온다.
    //id를 통해 받은 값을 다시 하면에 사용해야 하기 때문에 Modal 객체가 필요하다.
    public String findById(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "detail";
    }

    @GetMapping("/member/update")
    //내정보는 세션에 있기 때문에 호출
    //왜 이때만 HttpSession?
    public String updateForm(HttpSession session,Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember",memberDTO);
        return "update";
    }
    //redirect가 무엇인가
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/"+memberDTO.getId();
    }

    @GetMapping("/member/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        memberService.delete(id);
        return "redirect:/member/";
    }

    @GetMapping("/member/logout")
    public String logOut(HttpSession session){
        session.invalidate();
        return "redirect:/";

    }

    @PostMapping("/member/email-check")
    @ResponseBody // 꼭 추가해야 함! ajax의 경우 반드시 붙여야함
    public String emailCheck(@RequestParam("memberEmail") String memberEmail){
        System.out.println("memberEmail = " + memberEmail);
        boolean checkResult=memberService.emailCheck(memberEmail);
        if (checkResult){
            return "no";//중복 된다.

        }else
        {
            return "ok";//사용 가능
        }
    }
}
