package com.example.cafe.service;

import com.example.cafe.model.MenuItem;
import com.example.cafe.model.Order;
import com.example.cafe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order editOrder(Long id, Order new_order) {
        Optional<Order> old = orderRepository.findById(id);
        Order old_order = old.get();
        old_order.setOrderNumber(new_order.getOrderNumber());
        old_order.setCart(new_order.getCart());
        old_order.setCreatedAt(new_order.getCreatedAt());
        old_order.setStatus(new_order.getStatus());
        old_order.setUser(new_order.getUser());
        old_order.setPaidAt(new_order.getPaidAt());
        return orderRepository.save(old_order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
