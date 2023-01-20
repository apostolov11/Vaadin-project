package com.example.application.data.bean;

import com.example.application.data.entity.Person;

public class PersonReminderBean {

    private Long id;
    private String text;
    private String date;
    private Person person;

    public PersonReminderBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
