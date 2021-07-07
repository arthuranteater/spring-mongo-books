package com.example.springmongobooksexercise.services;


import com.example.springmongobooksexercise.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends MongoRepository<Book, String> {
    @Query("{'title': ?0}")
    List<Book> findAllByTitle(String name);
}