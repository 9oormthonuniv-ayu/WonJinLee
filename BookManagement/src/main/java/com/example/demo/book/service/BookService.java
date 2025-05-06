package com.example.demo.book.service;

import com.example.demo.book.dto.BookDTO;
import com.example.demo.book.entity.BookEntity;
import com.example.demo.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public void save(BookDTO bookDTO){
        bookDTO.setBookCurrentStock(bookDTO.getBookOriginalStock());
        BookEntity bookEntity=BookEntity.toBookEntity(bookDTO);
        bookRepository.save(bookEntity);
    }

    public List<BookDTO> bookFindAll(){
        List<BookEntity> bookEntityList=bookRepository.findAll();
        List<BookDTO> bookDTOList =new ArrayList<>();
        for (BookEntity bookEntity:bookEntityList) {
            bookDTOList.add(BookDTO.toBookDTO(bookEntity));

        }
        return bookDTOList;
    }

    public void deleteSelectedBooks(List<Long> bookIds){
        if (bookIds != null && !bookIds.isEmpty()) {
            for (Long id : bookIds) {
                bookRepository.deleteById(id);
            }
        }
    }
    // 책 ID로 단일 조회
    public BookDTO bookFindById(Long id) {
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        if (optionalBookEntity.isPresent()) {
            return BookDTO.toBookDTO(optionalBookEntity.get());
        } else {
            return null; // 또는 예외 처리 가능
        }
    }

    // 책 정보 수정
    public void bookUpdate(BookDTO bookDTO) {
        // 기존 데이터가 존재하는 경우 수정
        BookEntity bookEntity = BookEntity.toUpdateBookEntity(bookDTO);
        bookRepository.save(bookEntity); // save()는 ID가 있으면 update
    }


}
