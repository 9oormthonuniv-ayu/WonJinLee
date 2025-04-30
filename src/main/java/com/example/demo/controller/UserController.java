package com.example.demo.controller;

import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/admin/save")
    public String adminSaveForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("isAdmin", true);
        model.addAttribute("actionUrl", "/admin/save");
        return "save";
    }

    @PostMapping("/admin/save")
    public String adminSave(@ModelAttribute UserDTO userDTO) {
        userDTO.setAdmin(true);  // 필수!
        userService.save(userDTO);
        return "login";
    }


    @GetMapping("/user/save")
    public String saveForm(Model model){
        model.addAttribute("user", new UserDTO());
        model.addAttribute("isAdmin", false);
        model.addAttribute("actionUrl", "/user/save");
        return "save";
    }

    @PostMapping("/user/save")
    public String save(@ModelAttribute UserDTO userDTO){
        userService.save(userDTO);
        return "login";
    }

    @GetMapping("/user/login")
    public  String loginForm(){return "login";}

    @PostMapping("/user/login")
    public String login (@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO loginResult = userService.login(userDTO);
        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult);
            return "main";
        } else {
            return "login";
        }

    }
}