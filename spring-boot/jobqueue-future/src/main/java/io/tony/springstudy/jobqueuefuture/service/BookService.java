package io.tony.springstudy.jobqueuefuture.service;

import io.tony.springstudy.jobqueuefuture.model.Book;
import io.tony.springstudy.jobqueuefuture.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> list() {
        return bookRepository.findAll();
    }
}

