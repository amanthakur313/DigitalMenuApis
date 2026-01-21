

package com.otplogin.otplogin.service;

import com.otplogin.otplogin.dto.OrderRequest;
import com.otplogin.otplogin.model.*;
import com.otplogin.otplogin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private MenuRepository menuRepository;

    // Payment hata diya, ab seedha "Place Order" function hai
    public FoodOrder placeOrder(OrderRequest request) {
        FoodOrder order = new FoodOrder();
        
        // 1. Basic Details Set Karo
        order.setUserEmail(request.getEmail());
        order.setTableNumber(request.getTableNumber());
        order.setOrderTime(LocalDateTime.now());
        
        // 2. IMPORTANT: Payment nahi hai, isliye seedha CONFIRMED
        order.setStatus("CONFIRMED");

        // 3. Items Calculate Karo
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (OrderRequest.ItemRequest itemReq : request.getItems()) {
            MenuItem menuProduct = menuRepository.findById(itemReq.getMenuItemId()).orElse(null);
            
            if (menuProduct != null) {
                OrderItem orderItem = new OrderItem();
                orderItem.setMenuItemName(menuProduct.getName());
                orderItem.setPrice(menuProduct.getPrice());
                orderItem.setQuantity(itemReq.getQuantity());
                
                // Ye line bohot zaroori hai (Link item to order)
                orderItem.setOrder(order); 
                
                orderItems.add(orderItem);
                totalAmount += (menuProduct.getPrice() * itemReq.getQuantity());
            }
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalAmount);

        // 4. Database me save kar do
        return orderRepository.save(order);
    }

    // Admin ke liye list
    public List<FoodOrder> getAllOrders() {
        return orderRepository.findAllByOrderByOrderTimeDesc();
    }
    
    // User ke liye history
    public List<FoodOrder> getUserOrders(String email) {
        return orderRepository.findByUserEmailOrderByOrderTimeDesc(email);
    }
}