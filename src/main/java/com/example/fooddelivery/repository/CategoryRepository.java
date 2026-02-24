package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
