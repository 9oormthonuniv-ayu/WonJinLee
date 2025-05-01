package com.example.demo.service;

import com.example.demo.entity.BookEntity;
import com.example.demo.entity.RentalEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public void rentBook(Long bookId, Long userId) {
        BookEntity book = bookRepository.findById(bookId).orElseThrow();
        UserEntity user = userRepository.findById(userId).orElseThrow();

        RentalEntity rental = new RentalEntity();
        rental.setBook(book);
        rental.setUser(user);
        rental.setRentedAt(LocalDateTime.now());
        rental.setDueAt(LocalDateTime.now().plusDays(14)); // 예: 2주 대여
        rentalRepository.save(rental);
    }


}
