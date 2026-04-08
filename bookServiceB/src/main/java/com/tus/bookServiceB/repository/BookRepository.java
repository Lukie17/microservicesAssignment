package com.tus.bookServiceB.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tus.bookServiceB.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}