package com.example.cafe.service;

import com.example.cafe.model.Cart;
import com.example.cafe.model.CartItem;
import com.example.cafe.model.MenuItem;
import com.example.cafe.model.User;
import com.example.cafe.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    // КОРЗИНА

    @Autowired
    private CartItemService cartItemService;

    public Cart addMenuItemToCart(Long cartId, MenuItem menuItem, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Найти элемент корзины с этим блюдом
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItem.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Обновить количество и цену
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(menuItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemService.save(cartItem); // Обновляем в базе
        } else {
            // Добавить новый элемент в корзину
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setMenuItem(menuItem);
            newItem.setQuantity(quantity);
            newItem.setTotalPrice(menuItem.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cartItemService.save(newItem); // Сохраняем в базе
            cart.getCartItems().add(newItem); // Добавляем в корзину
        }

        // Пересчитать итоговую цену корзины
        recalculateCartTotalPrice(cart);

        return cartRepository.save(cart);
    }

    public Cart removeMenuItemFromCart(Long cartId, Long menuItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Найти элемент корзины с этим блюдом
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu item not found in cart"));

        // Удалить элемент из корзины и базы
        cart.getCartItems().remove(cartItem); // Удаляем из списка в корзине
        cartItemService.delete(cartItem.getId()); // Удаляем из базы

        // Пересчитать итоговую цену корзины
        recalculateCartTotalPrice(cart);

        return cartRepository.save(cart);
    }

    private void recalculateCartTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }

    public void printAllItemsInCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            System.out.println("Корзина пуста.");
        } else {
            for (CartItem cartItem : cartItems) {
                System.out.println("Товар: " + cartItem.getMenuItem().getName() +
                        ", количество: " + cartItem.getQuantity() +
                        ", цена за единицу: " + cartItem.getMenuItem().getPrice() +
                        ", общая стоимость: " + cartItem.getTotalPrice());
            }
        }
    }
}
