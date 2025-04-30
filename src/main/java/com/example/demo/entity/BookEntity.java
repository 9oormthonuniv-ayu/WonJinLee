package com.example.demo.entity;

import com.example.demo.dto.BookDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "book_table")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column
    private String bookTitle;

    @Column
    private String bookIsbn;

    @Column
    private String bookStock;

    @Column
    private String bookPublisher;

    @Column
    private String bookOublished_at;

    public static BookEntity toBookEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookId(bookDTO.getBookId());
        bookEntity.setBookTitle(bookDTO.getBookTitle());
        bookEntity.setBookIsbn(bookDTO.getBookIsbn());
        bookEntity.setBookStock(bookDTO.getBookStock());
        bookEntity.setBookPublisher(bookDTO.getBookPublisher());
        bookEntity.setBookOublished_at(bookDTO.getBookOublished_at());
        return bookEntity;
    }
}
