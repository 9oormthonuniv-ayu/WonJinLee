package com.example.demo.dto;

import com.example.demo.entity.BookEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDTO {

    private Long bookId;
    private String bookTitle;
    private String bookIsbn;
    private String bookStock;
    private String bookPublisher;
    private String bookOublished_at;

    public static BookDTO toBookDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(bookEntity.getBookId());
        bookDTO.setBookTitle(bookEntity.getBookTitle());
        bookDTO.setBookIsbn(bookEntity.getBookIsbn());
        bookDTO.setBookStock(bookEntity.getBookStock());
        bookDTO.setBookPublisher(bookEntity.getBookPublisher());
        bookDTO.setBookOublished_at(bookEntity.getBookOublished_at());
        return bookDTO;
    }
}

