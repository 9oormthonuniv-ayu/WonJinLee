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
    private Integer bookOriginalStock;

    @Column
    private Integer bookCurrentStock;

    @Column
    private String bookPublisher;

    @Column
    private String bookPublished_at;

    public static BookEntity toBookEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookId(bookDTO.getBookId());
        bookEntity.setBookTitle(bookDTO.getBookTitle());
        bookEntity.setBookIsbn(bookDTO.getBookIsbn());
        bookEntity.setBookOriginalStock(bookDTO.getBookOriginalStock());
        bookEntity.setBookCurrentStock(bookDTO.getBookCurrentStock());
        bookEntity.setBookPublisher(bookDTO.getBookPublisher());
        bookEntity.setBookPublished_at(bookDTO.getBookPublished_at());
        return bookEntity;
    }
    public static BookEntity toUpdateBookEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookId(bookDTO.getBookId());
        bookEntity.setBookTitle(bookDTO.getBookTitle());
        bookEntity.setBookIsbn(bookDTO.getBookIsbn());
        bookEntity.setBookOriginalStock(bookDTO.getBookOriginalStock());
        bookEntity.setBookCurrentStock(bookDTO.getBookCurrentStock());
        bookEntity.setBookPublisher(bookDTO.getBookPublisher());
        bookEntity.setBookPublished_at(bookDTO.getBookPublished_at());
        return bookEntity;
    }

}
