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

    @PostMapping
    public Order create(@RequestBody Order order) {

        System.out.println("👉 Controller HIT");

        Book book = orderService.getBook(order.getBookId());

        System.out.println("👉 Book returned: " + book);

        if (book.getId() == null) {
            System.out.println("🔥 Using fallback - book service unavailable");
            return repo.save(order);
        }

        return repo.save(order);
    }

    // GETALL
    @GetMapping
    public List<Order> getAll() {
        return repo.findAll();
    }
}