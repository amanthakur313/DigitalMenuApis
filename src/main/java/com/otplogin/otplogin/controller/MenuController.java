

package com.otplogin.otplogin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.otplogin.otplogin.model.MenuItem;
import com.otplogin.otplogin.service.MenuService;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // Add Item (With Image Upload)
    // Postman me Body -> form-data select karna
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuItem addItem(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image) throws IOException {
        
        return menuService.addMenuItem(name, description, price, category, image);
    }

    // Get All Items
    @GetMapping("/all")
    public List<MenuItem> getAllItems() {
        return menuService.getAllMenuItems();
    }

    // Update Item (With optional Image Upload)
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuItem updateItem(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("category") String category,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        
        return menuService.updateMenuItem(id, name, description, price, category, image);
    }

    // Delete Item
    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return Map.of("message", "Item deleted successfully");
    }
}