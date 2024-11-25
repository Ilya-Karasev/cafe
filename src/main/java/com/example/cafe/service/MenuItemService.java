package com.example.cafe.service;

import com.example.cafe.model.CartItem;
import com.example.cafe.model.MenuItem;
import com.example.cafe.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }
    public MenuItem editMenuItem(Long id, MenuItem new_cart_item) {
        Optional<MenuItem> old = menuItemRepository.findById(id);
        MenuItem old_menu_item = old.get();
        old_menu_item.setAvailable(new_cart_item.isAvailable());
        old_menu_item.setDescription(new_cart_item.getDescription());
        old_menu_item.setImg(new_cart_item.getImg());
        old_menu_item.setName(new_cart_item.getName());
        old_menu_item.setPrice(new_cart_item.getPrice());
        return menuItemRepository.save(old_menu_item);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
