package com.example.fooddelivery.repository;

import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish,Long> {
    public List<Dish> findDishByName(String name);
    public List<Dish> findDishByPrice(double price);
}
