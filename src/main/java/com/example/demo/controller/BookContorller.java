package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.service.BookService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookContorller {

    private final BookService bookService;

    @GetMapping("/book/save")
    public String saveFrom() {return "bookSave";}

    @PostMapping("/book/save")
    public  String save(@ModelAttribute BookDTO bookDTO, HttpSession session){
        bookService.save(bookDTO);
        return "redirect:/book";
    }

    @GetMapping("/book")
    public String listBooks(Model model) {
        List<BookDTO> books = bookService.bookFindAll();
        model.addAttribute("books", books);
        return "bookList";  // bookList.html
    }

}
