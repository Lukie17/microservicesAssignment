package com.tus.orderServiceA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tus.orderServiceA.dto.Book;
import com.tus.orderServiceA.entity.Order;
import com.tus.orderServiceA.repository.OrderRepository;
import com.tus.orderServiceA.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private OrderService orderService;

    // CREATE ORDER (calls Book Service)
    @PostMapping
    public Order create(@RequestBody Order order) {

        Book book = orderService.getBook(order.getBookId());

        if (book == null) {
            throw new RuntimeException("Book not found");
        }

        return repo.save(order);
    }

    // GET ALL
    @GetMapping
    public List<Order> getAll() {
        return repo.findAll();
    }
}