package com.tus.orderServiceA.controller;

import java.util.List;
import java.util.UUID;

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

    // CREATE
    @PostMapping
    public Order create(@RequestBody Order order) {

        String requestId = UUID.randomUUID().toString();
        System.out.println("👉 Request ID: " + requestId + " - Controller HIT");

        Book book = orderService.getBook(order.getBookId());
        System.out.println("👉 Book ID: " + book.getId());

        System.out.println("👉 Request ID: " + requestId + " - Book returned: " + book);

        if (book.getId() == null) {
            System.out.println("🔥 Request ID: " + requestId + " - Using fallback");
            return repo.save(order);
        }

        System.out.println("✅ Request ID: " + requestId + " - Order created successfully");
        return repo.save(order);
    }

    // GET ALL
    @GetMapping
    public List<Order> getAll() {
        return repo.findAll();
    }
    
    // GET ONE
    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }
    
    // UPDATE
    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody Order updated) {
        Order order = repo.findById(id).orElseThrow();
        order.setBookId(updated.getBookId());
        order.setQuantity(updated.getQuantity());
        return repo.save(order);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
    
}