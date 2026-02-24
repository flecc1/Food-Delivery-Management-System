package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish,Long> {
}
