package com.example.application.data.service;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Person;
import com.example.application.data.entity.Reminder;
import com.example.application.data.repository.ReminderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {

    private ReminderRepo reminderRepo;

    @Autowired
    public ReminderService(ReminderRepo reminderRepo) {
        this.reminderRepo = reminderRepo;
    }

    public Optional<Reminder> get(Long id) {
        return reminderRepo.findById(id);
    }

    public Reminder update(Reminder entity) {
        return reminderRepo.save(entity);
    }

    public void delete(Long id) {
        reminderRepo.deleteById(id);
    }

    public Page<Reminder> list(Pageable pageable) {
        return reminderRepo.findAll(pageable);
    }

    public Page<Reminder> list(Pageable pageable, Specification<Reminder> filter) {
        return reminderRepo.findAll(filter, pageable);
    }

    public List<Reminder> findByPerson(Person person) {
        return person == null ? new ArrayList<>() : (reminderRepo.findByPerson(person));
    }
}
