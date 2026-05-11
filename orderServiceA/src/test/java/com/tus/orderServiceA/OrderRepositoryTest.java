package com.tus.orderServiceA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tus.orderServiceA.entity.Order;
import com.tus.orderServiceA.repository.OrderRepository;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Test
    void shouldSaveOrder() {

        Order order = new Order();
        order.setBookId(1L);
        order.setQuantity(3);

        Order saved = repository.save(order);

        assertNotNull(saved.getId());
        assertEquals(3, saved.getQuantity());
    }
}