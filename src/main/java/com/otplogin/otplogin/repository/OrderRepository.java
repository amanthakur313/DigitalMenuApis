package com.otplogin.otplogin.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.otplogin.otplogin.model.FoodOrder;
import java.util.List;

public interface OrderRepository extends JpaRepository<FoodOrder, Long> {
 
    
    // Admin: All orders sorted by time
    List<FoodOrder> findAllByOrderByOrderTimeDesc();
    
    // User: Their specific orders
    List<FoodOrder> findByUserEmailOrderByOrderTimeDesc(String userEmail);
}