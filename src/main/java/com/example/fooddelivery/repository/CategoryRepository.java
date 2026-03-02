package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findCategoryByName(String name);
}
