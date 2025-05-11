package com.example.demo.user.controller;

import com.example.demo.user.dto.UserDTO;
import com.example.demo.rental.entity.RentalEntity;
import com.example.demo.rental.service.RentalService;
import com.example.demo.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RentalService rentalService;

    @GetMapping("/admin/save")
    public String adminSaveForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("isAdmin", true);
        model.addAttribute("actionUrl", "/admin/save");
        return "user/userSave";
    }

    @PostMapping("/admin/save")
    public String adminSave(@ModelAttribute UserDTO userDTO) {
        userDTO.setAdmin(true);  // 필수!
        userService.save(userDTO);
        return "user/userLogin";
    }


    @GetMapping("/user/save")
    public String saveForm(Model model){
        model.addAttribute("user", new UserDTO());
        model.addAttribute("isAdmin", false);
//        model.addAttribute("actionUrl", "/user/save");
        return "user/userSave";
    }

    @PostMapping("/user/save")
    public String save(@ModelAttribute UserDTO userDTO){
        userService.save(userDTO);
        return "user/userLogin";
    }

    @GetMapping("/user/login")
    public  String loginForm(){return "user/userLogin";}

    @PostMapping("/user/login")
    public String login (@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO loginResult = userService.login(userDTO);
        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult);
            if (loginResult.isAdmin()) {
                return "redirect:/admin";
            } else {
                return "redirect:/main";
            }
        } else {
            return "user/userLogin";
        }
    }

    @GetMapping("/admin")
    public String adminMain(){return "admin/adminMain";}

    @GetMapping("/main")
    public String main(){return "user/userMain";}

    @GetMapping("/admin/user")
    public String userList(Model model){
    List<UserDTO> users = userService.userFindAll();
    model.addAttribute("users", users);
    return "admin/adminUs   erList";
    }

    @PostMapping("/admin/user")
    public String deleteSelectedUsers(@RequestParam("userIds") List<Long> userIds) {
        userService.deleteSelectedUsers(userIds);
        return "redirect:/admin/user";  // 삭제 후 다시 목록으로 리다이렉트
    }

    @GetMapping("/user/rentals")
    public String myRentals(HttpSession session, Model model) {
        UserDTO loginUser = (UserDTO) session.getAttribute("loginEmail");
        Long userId = loginUser.getUserId();

        List<RentalEntity> rentals = rentalService.findRentalsByUserId(userId);
        model.addAttribute("rentals", rentals);

        return "user/userRentals";
    }

}