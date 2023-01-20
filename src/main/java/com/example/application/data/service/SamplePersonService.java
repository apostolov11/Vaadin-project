package com.example.application.data.service;

import com.example.application.data.entity.Person;

import java.util.List;
import java.util.Optional;

import com.example.application.data.repository.SamplePersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SamplePersonService {

    private final SamplePersonRepository repository;

    public SamplePersonService(SamplePersonRepository repository) {
        this.repository = repository;
    }

    public Optional<Person> get(Long id) {
        return repository.findById(id);
    }

    public Person update(Person person) {
        return repository.save(person);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Person> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Person> list(Pageable pageable, Specification<Person> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

}
