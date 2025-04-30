package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.BookEntity;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public void save(BookDTO bookDTO){
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

}
