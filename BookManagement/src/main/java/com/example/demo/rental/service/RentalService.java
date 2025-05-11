package com.example.demo.rental.service;

import com.example.demo.book.entity.BookEntity;
import com.example.demo.rental.entity.RentalEntity;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.book.repository.BookRepository;
import com.example.demo.rental.repository.RentalRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public void rentBook(Long bookId, Long userId) {
        BookEntity book = bookRepository.findById(bookId).orElseThrow();
        UserEntity user = userRepository.findById(userId).orElseThrow();

        if (book.getBookCurrentStock() <= 0) {
            throw new IllegalStateException("📕 대여 가능한 재고가 없습니다.");
        }
        book.setBookCurrentStock(book.getBookCurrentStock() - 1);
        bookRepository.save(book);

        RentalEntity rental = new RentalEntity();
        rental.setBook(book);
        rental.setUser(user);
        rental.setRentedAt(LocalDateTime.now());
        rental.setRentalDueAt(LocalDateTime.now().plusDays(14)); // 예: 2주 대여
        rentalRepository.save(rental);
    }
    @Transactional
    public void returnBook(Long rentalId) {
        RentalEntity rental = rentalRepository.findById(rentalId).orElseThrow();

        if (rental.getReturnedAt() != null) {
            throw new IllegalStateException("이미 반납된 도서입니다.");
        }

        // 반납 시간 설정
        rental.setReturnedAt(LocalDateTime.now());
        rentalRepository.save(rental);

        // 도서 재고 증가
        BookEntity book = rental.getBook();
        book.setBookCurrentStock(book.getBookCurrentStock() + 1);
        bookRepository.save(book);
    }

    public List<RentalEntity> findRentalsByUserId(Long userId) {
        return rentalRepository.findByUser_UserIdAndReturnedAtIsNull(userId);
    }

}
