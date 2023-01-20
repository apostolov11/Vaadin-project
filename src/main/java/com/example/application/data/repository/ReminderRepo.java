package com.example.application.data.repository;


import com.example.application.data.entity.Book;
import com.example.application.data.entity.Person;
import com.example.application.data.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepo extends JpaRepository<Reminder, Long>, JpaSpecificationExecutor<Reminder> {


    List<Reminder> findByPerson(Person person);
}
