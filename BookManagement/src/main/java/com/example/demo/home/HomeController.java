package com.example.demo.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {
    //기본페이지 요청
    @GetMapping("/")//기본 요청이 들어오면 아래 머세드가 호출
    public String index(){
        return "index";//templates 폴더의 index.html을 찾아간다.
    }

}
