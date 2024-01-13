package com.lexie.springbootbooks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexie.springbootbooks.TestDataUtil;
import com.lexie.springbootbooks.domain.dto.BookDto;
import com.lexie.springbootbooks.domain.entities.BookEntity;
import com.lexie.springbootbooks.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntgrationTests {

    private MockMvc mockMvc;

    private BookService bookService;

    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntgrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldCreateBookAndReturn201() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void shouldCreateBookAndReturnCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(bookDto.getAuthor())
        );
    }

    @Test
    public void shouldListBooksAndReturn200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldListBooksAndReturnListOfBooks() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        bookService.createOrUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBookEntityA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].author").value(testBookEntityA.getAuthorEntity())
        );
    }

    @Test
    public void shouldGetBookAndReturn200WhenBookExists() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        bookService.createOrUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldGetBookAndReturn404WhenBookDoesNotExist() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        bookService.createOrUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/99")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldGetBookAndReturnBookWhenBookExists() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        bookService.createOrUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookEntityA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(testBookEntityA.getAuthorEntity())
        );
    }

    @Test
    public void shouldUpdateBookAndReturn200() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBookEntity = bookService.createOrUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setIsbn(savedBookEntity.getIsbn());
        String bookJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldUpdateBookAndReturnBook() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBookEntity = bookService.createOrUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setIsbn(savedBookEntity.getIsbn());
        testBookDto.setTitle("Harry Potter");
        String bookJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(testBookDto.getAuthor())
        );
    }

    @Test
    public void shouldPartialUpdateBookAndReturn200() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBookEntity = bookService.createOrUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setTitle("Harry Potter");
        String bookJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldPartialUpdateBookAndReturnBook() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBookEntity = bookService.createOrUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setTitle("Harry Potter");
        String bookJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(testBookDto.getAuthor())
        );
    }
}
