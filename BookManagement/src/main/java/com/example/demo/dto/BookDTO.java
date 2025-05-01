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
    private Integer bookOriginalStock;
    private Integer bookCurrentStock;
    private String bookPublisher;
    private String bookPublished_at;

    public static BookDTO toBookDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(bookEntity.getBookId());
        bookDTO.setBookTitle(bookEntity.getBookTitle());
        bookDTO.setBookIsbn(bookEntity.getBookIsbn());
        bookDTO.setBookOriginalStock(bookEntity.getBookOriginalStock());
        bookDTO.setBookCurrentStock(bookEntity.getBookCurrentStock());
        bookDTO.setBookPublisher(bookEntity.getBookPublisher());
        bookDTO.setBookPublished_at(bookEntity.getBookPublished_at());
        return bookDTO;
    }
}

