package com.example.springmongobooksexercise.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document
public class Book {

    //Title
    //Author
    //Length (pages)
    //Price

    @Id
    private String bookId;
    private String title;
    private String author;
    private Long pages;
    private BigDecimal price;
    private Date creationDate = new Date();

    //Constructors

    public Book(){}

    public Book(String title) {
        this.title = title;
    }

    //Getters/Setters

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Long getPages() {
        return pages;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getCreationDate() {
        return creationDate;
    }




    //Overrides

    @Override
    public String toString() {
        return String.format("title = " + title + " author = " + author);
    }
}
