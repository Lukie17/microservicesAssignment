package com.tus.orderServiceA;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.tus.orderServiceA.dto.Book;
import com.tus.orderServiceA.service.OrderService;
import org.mockito.InjectMocks;
//test

class OrderServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnBookWhenBookServiceResponds() {

        Book mockBook = new Book();
        mockBook.setId(1L);

        when(restTemplate.getForObject(
                "http://bookserviceb/books/1",
                Book.class))
                .thenReturn(mockBook);

        Book result = orderService.getBook(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void fallbackShouldReturnEmptyBook() {

        Book result = orderService.fallbackBook(1L,
                new RuntimeException("Service Down"));

        assertNotNull(result);
        assertNull(result.getId());
    }
    @Test
    void shouldReturnFallbackBookWhenServiceFails() {

        when(restTemplate.getForObject(anyString(), eq(Book.class)))
                .thenThrow(new RuntimeException());

        Book result = orderService.fallbackBook(
                1L,
                new RuntimeException("Service Down"));

        assertNotNull(result);
        assertNull(result.getId());
    }
    @Test
    void shouldReturnCorrectBookId() {

        Book mockBook = new Book();
        mockBook.setId(5L);

        when(restTemplate.getForObject(anyString(), eq(Book.class)))
                .thenReturn(mockBook);

        Book result = orderService.getBook(5L);

        assertEquals(5L, result.getId());
    }
    @Test
    void shouldReturnBookWithCorrectTitle() {

        Book mockBook = new Book();
        mockBook.setId(10L);
        mockBook.setTitle("Spring Boot");

        when(restTemplate.getForObject(anyString(), eq(Book.class)))
                .thenReturn(mockBook);

        Book result = orderService.getBook(10L);

        assertEquals("Spring Boot", result.getTitle());
    }

    @Test
    void shouldReturnBookWithCorrectAuthor() {

        Book mockBook = new Book();
        mockBook.setAuthor("Luke");

        when(restTemplate.getForObject(anyString(), eq(Book.class)))
                .thenReturn(mockBook);

        Book result = orderService.getBook(2L);

        assertEquals("Luke", result.getAuthor());
    }

    @Test
    void shouldReturnBookWithCorrectPrice() {

        Book mockBook = new Book();
        mockBook.setPrice(29.99);

        when(restTemplate.getForObject(anyString(), eq(Book.class)))
                .thenReturn(mockBook);

        Book result = orderService.getBook(3L);

        assertEquals(29.99, result.getPrice());
    }

    @Test
    void shouldReturnDifferentBookIds() {

        Book mockBook = new Book();
        mockBook.setId(99L);

        when(restTemplate.getForObject(anyString(), eq(Book.class)))
                .thenReturn(mockBook);

        Book result = orderService.getBook(99L);

        assertNotEquals(1L, result.getId());
    }

    @Test
    void fallbackBookShouldNotHaveTitle() {

        Book result = orderService.fallbackBook(
                1L,
                new RuntimeException("Failure"));

        assertNull(result.getTitle());
    }

    @Test
    void fallbackBookShouldNotHaveAuthor() {

        Book result = orderService.fallbackBook(
                1L,
                new RuntimeException("Failure"));

        assertNull(result.getAuthor());
    }
}