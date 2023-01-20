package com.example.application.data.repository;

import com.example.application.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SamplePersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {


}
