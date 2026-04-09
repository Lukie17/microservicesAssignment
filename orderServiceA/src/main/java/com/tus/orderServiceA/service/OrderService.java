package com.tus.orderServiceA.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tus.orderServiceA.dto.Book;

@Service
public class OrderService {

    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Book getBook(Long bookId) {
        return restTemplate.getForObject(
                "http://BOOKSERVICEB/books/" + bookId,
                Book.class
        );
    }
}