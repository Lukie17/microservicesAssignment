package com.tus.bookServiceB.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tus.bookServiceB.entity.Book;
import com.tus.bookServiceB.repository.BookRepository;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository repo;

    @PostMapping
    public Book create(@RequestBody Book book) {
        return repo.save(book);
    }

    @GetMapping
    public List<Book> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book updated) {
        Book book = repo.findById(id).orElseThrow();
        book.setTitle(updated.getTitle());
        book.setAuthor(updated.getAuthor());
        book.setPrice(updated.getPrice());
        return repo.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}