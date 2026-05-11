package com.tus.orderServiceA;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tus.orderServiceA.controller.OrderController;
import com.tus.orderServiceA.dto.Book;
import com.tus.orderServiceA.entity.Order;
import com.tus.orderServiceA.repository.OrderRepository;
import com.tus.orderServiceA.service.OrderService;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository repo;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllOrders() throws Exception {

        Order order = new Order();
        order.setId(1L);
        order.setBookId(2L);
        order.setQuantity(5);

        when(repo.findAll()).thenReturn(List.of(order));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldCreateOrder() throws Exception {

        Order order = new Order();
        order.setBookId(1L);
        order.setQuantity(2);

        Book book = new Book();
        book.setId(1L);

        when(orderService.getBook(1L)).thenReturn(book);
        when(repo.save(org.mockito.ArgumentMatchers.any(Order.class)))
                .thenReturn(order);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1));
    }
    @Test
    void shouldReturnEmptyOrderList() throws Exception {

        when(repo.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldReturnCorrectQuantity() throws Exception {

        Order order = new Order();
        order.setId(2L);
        order.setBookId(1L);
        order.setQuantity(10);

        when(repo.findAll()).thenReturn(List.of(order));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(10));
    }
}
