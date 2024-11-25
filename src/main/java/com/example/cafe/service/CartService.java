package com.example.cafe.service;

import com.example.cafe.model.Cart;
import com.example.cafe.model.User;
import com.example.cafe.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart editCart(Long id, Cart new_cart) {
        Optional<Cart> old = cartRepository.findById(id);
        Cart old_cart = old.get();
        old_cart.setActive(new_cart.isActive());
        old_cart.setCreatedAt(new_cart.getCreatedAt());
        old_cart.setUser(new_cart.getUser());
        old_cart.setUpdatedAt(new_cart.getUpdatedAt());
        old_cart.setTotalPrice(new_cart.getTotalPrice());
        return cartRepository.save(old_cart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
}
