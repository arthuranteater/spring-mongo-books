package com.example.springmongobooksexercise.controllers;

import com.example.springmongobooksexercise.models.Book;
import com.example.springmongobooksexercise.services.BookRepo;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;


@WebMvcTest(BooksController.class)
final class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepo bookRepo;


    //Class Exercise: Create a web service in Java that will perform CRUD operations for a bookstore.

    //CRUD - Create, Read, Update, Delete

    //N-Zombies - Null, Zero, Many - Boundaries, Interfaces, Exceptions

    //SIMPLE - build off previous test

    //1 - Zero
    //given there are no books, when I make a GET request, I will see an empty array list

    @Test
    public void zero_books_returns_empty_array() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        List<Book> bookList = new ArrayList<Book>();
        when(bookRepo.findAll()).thenReturn(bookList);
        this.mockMvc.perform(request).andExpect(content().string("[]"));
    }

    //2 - One, Many
    //given there is one book, when I make a GET request, I will see an array list with one book

    @Test
    public void one_book_returns_array_of_one() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        List<Book> bookList = new ArrayList<Book>();
        bookList.add(new Book("Book Title 1"));
        when(bookRepo.findAll()).thenReturn(bookList);
        this.mockMvc.perform(request).andExpect(jsonPath("$[0].title", is("Book Title 1"))).andExpect(jsonPath("$", hasSize(1)));
    }

    //3 - Many
    //given there are two books, when I make a GET request, I will see an array list with two books


    @Test
    public void two_books_returns_array_of_two() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        List<Book> bookList = new ArrayList<Book>();
        bookList.add(new Book("Book Title 1"));
        bookList.add(new Book("Book Title 2"));
        when(bookRepo.findAll()).thenReturn(bookList);
        this.mockMvc.perform(request).andExpect(jsonPath("$[0].title", is("Book Title 1"))).andExpect(jsonPath("$[1].title", is("Book Title 2"))).andExpect(jsonPath("$", hasSize(2)));
    }

    //4 - Delete
    //given I have the correct bookId, when I make a DELETE request with the bookId, I will see that an item has been deleted

    @Test
    public void delete_returns_book_deleted() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/1");
        when(bookRepo.existsById("1")).thenReturn(true);
        this.mockMvc.perform(request).andExpect(content().string(containsString("1")));
        verify(bookRepo).deleteById(any());
    }

    //4 - Create
    //given I have information for a new book, when I make a POST request with the new book, I will see the new book returned

    @Test
    public void post_returns_book_saved() throws Exception {
        Book book1 = new Book("Book Title 1");
        ObjectMapper mapper = new ObjectMapper();
        //can't save the specific instance of book1 https://mkyong.com/spring-boot/spring-mockito-unable-to-mock-save-method/
        when(bookRepo.save(any(Book.class))).thenReturn(book1);
        RequestBuilder request = MockMvcRequestBuilders.post("/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(book1));
        this.mockMvc.perform(request).andExpect(jsonPath("$.title", is("Book Title 1")));
    }

    //4 - Update
    //given I want to update the book with the bookId, when I make a PUT request with the bookId and updated book, I will see the updated book returned

    @Test
    public void put_returns_book_updated() throws Exception {
        Book book1 = new Book("Book Title 1");
        ObjectMapper mapper = new ObjectMapper();
        when(bookRepo.findById(any())).thenReturn(java.util.Optional.of(book1));
        Book updBook = new Book("Updated Title");
        when(bookRepo.save(any(Book.class))).thenReturn(updBook);
        RequestBuilder request = MockMvcRequestBuilders.put("/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(updBook));
        this.mockMvc.perform(request).andExpect(jsonPath("$.title", is("Updated Title")));
    }



}


