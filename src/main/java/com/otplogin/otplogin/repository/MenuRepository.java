package com.otplogin.otplogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otplogin.otplogin.model.MenuItem;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem,Long>{
	

}
