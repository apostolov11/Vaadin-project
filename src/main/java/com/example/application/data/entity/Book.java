package com.example.application.data.entity;




import javax.persistence.*;

@Entity
@Table
public class Book extends AbstractEntity {

    private String bookName;
    private String authorName;
    private String genre;
    private String rating;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id",referencedColumnName = "id", nullable = false)
    private Person person;


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
