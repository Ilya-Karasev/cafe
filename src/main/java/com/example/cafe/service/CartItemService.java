package com.example.cafe.service;

import com.example.cafe.model.CartItem;
import com.example.cafe.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    public CartItem createCartItem(CartItem cart) {
        return cartItemRepository.save(cart);
    }

    public CartItem editCartItem(Long id, CartItem new_cart_item) {
        Optional<CartItem> old = cartItemRepository.findById(id);
        CartItem old_cart_item = old.get();
        old_cart_item.setCart(new_cart_item.getCart());
        old_cart_item.setMenuItem(new_cart_item.getMenuItem());
        old_cart_item.setQuantity(new_cart_item.getQuantity());
        old_cart_item.setTotalPrice(new_cart_item.getTotalPrice());
        return cartItemRepository.save(old_cart_item);
    }

    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    // КОРЗИНА

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void delete(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}