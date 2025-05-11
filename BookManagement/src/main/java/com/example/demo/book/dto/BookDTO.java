package com.example.demo.book.dto;

import com.example.demo.book.entity.BookEntity;
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
    private Integer bookOriginalStock;
    private Integer bookCurrentStock;
    private String bookPublisher;
    private String bookPublishedAt;

    public static BookDTO toBookDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(bookEntity.getBookId());
        bookDTO.setBookTitle(bookEntity.getBookTitle());
        bookDTO.setBookIsbn(bookEntity.getBookIsbn());
        bookDTO.setBookOriginalStock(bookEntity.getBookOriginalStock());
        bookDTO.setBookCurrentStock(bookEntity.getBookCurrentStock());
        bookDTO.setBookPublisher(bookEntity.getBookPublisher());
        bookDTO.setBookPublishedAt(bookEntity.getBookPublishedAt());
        return bookDTO;
    }
}

