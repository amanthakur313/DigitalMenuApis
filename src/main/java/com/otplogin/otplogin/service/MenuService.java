//package com.otplogin.otplogin.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.otplogin.otplogin.model.MenuItem;
//import com.otplogin.otplogin.repository.MenuRepository;
//
//@Service
//public class MenuService {
//
//    @Autowired
//    private MenuRepository menuRepository;
//
//    // 1. Add New Item
//    public MenuItem addMenuItem(MenuItem item) {
//        return menuRepository.save(item);
//    }
//
//    // 2. Get All Items
//    public List<MenuItem> getAllMenuItems() {
//        return menuRepository.findAll();
//    }
//
//    // 3. Update Item
//    public MenuItem updateMenuItem(Long id, MenuItem updatedItem) {
//        Optional<MenuItem> existingItem = menuRepository.findById(id);
//
//        if (existingItem.isPresent()) {
//            MenuItem item = existingItem.get();
//            item.setName(updatedItem.getName());
//            item.setPrice(updatedItem.getPrice());
//            item.setDescription(updatedItem.getDescription());
//            item.setCategory(updatedItem.getCategory());
//            item.setImageUrl(updatedItem.getImageUrl());
//            return menuRepository.save(item);
//        } else {
//            return null; // Item nahi mila
//        }
//    }
//
//    // 4. Delete Item
//    public void deleteMenuItem(Long id) {
//        menuRepository.deleteById(id);
//    }
//}

package com.otplogin.otplogin.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.otplogin.otplogin.model.MenuItem;
import com.otplogin.otplogin.repository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    // Project folder ke bahar 'uploads' naam ka folder banega
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    // Helper method to save file
    private String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) return null;

        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs(); // Folder create karo agar nahi hai
        }

        // Unique filename generate karo
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        
        // File save karo
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return URL path
        return "/uploads/" + fileName;
    }

    // 1. Add New Item with Image
    public MenuItem addMenuItem(String name, String description, Double price, String category, MultipartFile imageFile) throws IOException {
        String imageUrl = saveImage(imageFile);

        MenuItem item = new MenuItem();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);
        item.setImageUrl(imageUrl);

        return menuRepository.save(item);
    }

    // 2. Get All Items
    public List<MenuItem> getAllMenuItems() {
        return menuRepository.findAll();
    }

    // 3. Update Item with Image
    public MenuItem updateMenuItem(Long id, String name, String description, Double price, String category, MultipartFile imageFile) throws IOException {
        Optional<MenuItem> existingItem = menuRepository.findById(id);

        if (existingItem.isPresent()) {
            MenuItem item = existingItem.get();
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setCategory(category);

            // Agar nayi image aayi hai to hi update karo, warna purani rehne do
            if (imageFile != null && !imageFile.isEmpty()) {
                String newImageUrl = saveImage(imageFile);
                item.setImageUrl(newImageUrl);
            }

            return menuRepository.save(item);
        } else {
            return null;
        }
    }

    // 4. Delete Item
    public void deleteMenuItem(Long id) {
        menuRepository.deleteById(id);
    }
}
