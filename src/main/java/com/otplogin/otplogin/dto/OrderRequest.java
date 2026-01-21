package com.otplogin.otplogin.dto;

import java.util.List;

public class OrderRequest {

    private String email;
    private String tableNumber;
    private List<ItemRequest> items;

    // --- INNER CLASS (Ek Item ke liye) ---
    public static class ItemRequest {
        private Long menuItemId;
        private Integer quantity;

        // Getters and Setters for ItemRequest
        public Long getMenuItemId() { return menuItemId; }
        public void setMenuItemId(Long menuItemId) { this.menuItemId = menuItemId; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    // --- GETTERS AND SETTERS (Main Class) ---
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTableNumber() { return tableNumber; }
    public void setTableNumber(String tableNumber) { this.tableNumber = tableNumber; }

    public List<ItemRequest> getItems() { return items; }
    public void setItems(List<ItemRequest> items) { this.items = items; }
}