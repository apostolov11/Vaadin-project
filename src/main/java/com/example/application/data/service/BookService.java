package com.example.application.data.service;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Person;
import com.example.application.data.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> get(Long id) {
        return bookRepository.findById(id);
    }

    public Book update(Book entity) {
        return bookRepository.save(entity);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public Page<Book> list(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Page<Book> list(Pageable pageable, Specification<Book> filter) {
        return bookRepository.findAll(filter, pageable);
    }

    public int count() {
        return (int) bookRepository.count();
    }

    public List<Book>findByPerson(Person person) {
    return person == null ? new ArrayList<>() : (bookRepository.findByPerson(person));
    }

    public Book findByPersonId (Long personId) {
        return bookRepository.findByPersonId(personId);
    }
}
