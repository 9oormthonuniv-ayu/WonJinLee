package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.RentalRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.RentalService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final RentalService rentalService;
    private final BookService bookService;

    @GetMapping("/book/save")
    public String saveFrom() {return "bookSave";}

    @PostMapping("/book/save")
    public  String save(@ModelAttribute BookDTO bookDTO, HttpSession session){
        bookService.save(bookDTO);
        return "redirect:/admin";
    }

    @GetMapping("/book")
    public String listBooks(Model model) {
        List<BookDTO> books = bookService.bookFindAll();
        model.addAttribute("books", books);
        return "bookList";  // bookList.html
    }

    @PostMapping("/admin/book")
    public String deleteBooks(@RequestParam("bookIds") List<Long> bookIds) {
        bookService.deleteSelectedBooks(bookIds);
        return "redirect:/admin/book";
    }

    @GetMapping("/admin/book")
    public String adminBookList(Model model){
        List<BookDTO> books = bookService.bookFindAll();
        model.addAttribute("books", books);
        return "adminBookList";
    }
    @GetMapping("/book/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        BookDTO bookDTO = bookService.bookFindById(id);
        model.addAttribute("book", bookDTO);
        return "bookUpdate"; // bookUpdate.html 로 이동
    }
    @PostMapping("/book/update")
    public String update(@ModelAttribute BookDTO bookDTO) {
        bookService.bookUpdate(bookDTO);
        return "redirect:/admin/book"; // 수정 완료 후 관리자 목록으로 이동
    }
    @PostMapping("/book/rent/{bookId}")
    public String rentBook(@PathVariable("bookId") Long bookId, HttpSession session, RedirectAttributes redirectAttributes) {

        UserDTO loginUser = (UserDTO) session.getAttribute("loginEmail");
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        rentalService.rentBook(bookId, loginUser.getUserId());
        return "redirect:/book"; // 대여 후 책 목록으로 이동
    }

    @PostMapping("/book/return/{rentalId}")
    public String returnBook(@PathVariable Long rentalId, RedirectAttributes redirectAttributes) {
        try {
            rentalService.returnBook(rentalId);
            redirectAttributes.addFlashAttribute("message", "📗 반납 완료되었습니다.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/user/rentals"; // 나중에 유저 대여 목록으로 바꾸면 더 좋음
    }

}
