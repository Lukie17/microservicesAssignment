package com.tus.orderServiceA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tus.orderServiceA.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}