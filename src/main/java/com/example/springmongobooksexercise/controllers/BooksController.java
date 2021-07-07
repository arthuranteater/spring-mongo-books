package com.example.springmongobooksexercise.controllers;

import com.example.springmongobooksexercise.models.Book;
import com.example.springmongobooksexercise.services.BookRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("/")
public class BooksController {

    private final BookRepo bookRepo;

    public BooksController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping("")
    public @ResponseBody List<Book> getAll(){
        return bookRepo.findAll();
    }

    @DeleteMapping("/{bookId}")
    public String delBook(@PathVariable String bookId) {
        boolean bookExists = bookRepo.existsById(bookId);
        if(bookExists) {
            bookRepo.deleteById(bookId);
            return "deleted bookId" + bookId;
        }
        return "couldn't find bookId" + bookId;
    }

    @PostMapping("")
    public @ResponseBody Book createBook(@RequestBody Book book) {
        return bookRepo.save(book);
    }

    @PutMapping("/{bookId}")
    public @ResponseBody Book Book(@PathVariable String bookId, @RequestBody Book book) {
        Book eBook = bookRepo.findById(bookId).orElse(null);
        if (eBook != null) {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> eBookMap = mapper.convertValue(eBook, HashMap.class);
            HashMap<String, Object> bookMap = mapper.convertValue(book, HashMap.class);
            bookMap.values().removeAll(Collections.singleton(null));
            eBookMap.putAll(bookMap);
            Book updBook  = mapper.convertValue(eBookMap, Book.class);
            return bookRepo.save(updBook);
        }
        return bookRepo.save(book);
    }









}
