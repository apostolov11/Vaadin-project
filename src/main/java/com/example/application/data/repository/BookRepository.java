package com.example.application.data.repository;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository  extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    List<Book> findByPerson(Person person);

    Book findByPersonId(Long personId);
}
