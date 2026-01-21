package com.otplogin.otplogin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otplogin.otplogin.model.MenuItem;
import com.otplogin.otplogin.repository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    // 1. Add New Item
    public MenuItem addMenuItem(MenuItem item) {
        return menuRepository.save(item);
    }

    // 2. Get All Items
    public List<MenuItem> getAllMenuItems() {
        return menuRepository.findAll();
    }

    // 3. Update Item
    public MenuItem updateMenuItem(Long id, MenuItem updatedItem) {
        Optional<MenuItem> existingItem = menuRepository.findById(id);

        if (existingItem.isPresent()) {
            MenuItem item = existingItem.get();
            item.setName(updatedItem.getName());
            item.setPrice(updatedItem.getPrice());
            item.setDescription(updatedItem.getDescription());
            item.setCategory(updatedItem.getCategory());
            item.setImageUrl(updatedItem.getImageUrl());
            return menuRepository.save(item);
        } else {
            return null; // Item nahi mila
        }
    }

    // 4. Delete Item
    public void deleteMenuItem(Long id) {
        menuRepository.deleteById(id);
    }
}