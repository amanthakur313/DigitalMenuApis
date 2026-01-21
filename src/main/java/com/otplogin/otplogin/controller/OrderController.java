

package com.otplogin.otplogin.controller;

import com.otplogin.otplogin.dto.OrderRequest;
import com.otplogin.otplogin.model.FoodOrder;
import com.otplogin.otplogin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // --- USER APIs ---

    // 1. Order Place Karne Ke Liye (No Payment)
    // URL: POST http://localhost:8081/api/orders/place
    @PostMapping("/place")
    public FoodOrder placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }

    // 2. User Apni Order History Dekhne Ke Liye
    // URL: GET http://localhost:8081/api/orders/my-orders?email=user@gmail.com
    @GetMapping("/my-orders")
    public List<FoodOrder> getMyOrders(@RequestParam String email) {
        return orderService.getUserOrders(email);
    }

    // --- ADMIN APIs ---

    // 3. Admin Saare Orders Dekhne Ke Liye
    // URL: GET http://localhost:8081/api/orders/all
    @GetMapping("/all")
    public List<FoodOrder> getAllOrders() {
        return orderService.getAllOrders();
    }
}