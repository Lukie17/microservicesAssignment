package com.tus.orderServiceA.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tus.orderServiceA.dto.Book;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderService {

    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "bookService", fallbackMethod = "fallbackBook")
    public Book getBook(Long bookId) {
    	System.out.println("➡️ Calling BookService for bookId: " + bookId);
    	
        return restTemplate.getForObject(
                "http://bookserviceb/books/" + bookId,
                Book.class
        );
    }

    public Book fallbackBook(Long bookId, Throwable t) {
        //System.out.println("Book service is DOWN!");
    	System.out.println("🚨 FALLBACK triggered for bookId: " + bookId);
        System.out.println("Reason: " + t.getMessage());
    	
    	return new Book();
    }
}