package com.otplogin.otplogin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.otplogin.otplogin.model.MenuItem;
import com.otplogin.otplogin.service.MenuService;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // URL: POST http://localhost:8081/api/menu/add
    @PostMapping("/add")
    public MenuItem addItem(@RequestBody MenuItem item) {
        return menuService.addMenuItem(item);
    }

    // URL: GET http://localhost:8081/api/menu/all
    @GetMapping("/all")
    public List<MenuItem> getAllItems() {
        return menuService.getAllMenuItems();
    }

    // URL: PUT http://localhost:8081/api/menu/update/1  (Yahan 1 ID hai)
    @PutMapping("/update/{id}")
    public MenuItem updateItem(@PathVariable Long id, @RequestBody MenuItem item) {
        return menuService.updateMenuItem(id, item);
    }

    // URL: DELETE http://localhost:8081/api/menu/delete/1
    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return Map.of("message", "Item deleted successfully");
    }
}