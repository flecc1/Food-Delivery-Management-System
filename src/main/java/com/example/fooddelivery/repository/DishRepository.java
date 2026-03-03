package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Dish;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {
    @Override
    @EntityGraph(attributePaths = {"category", "menus.restaurant"})
    @NullMarked
    Optional<Dish> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"category", "menus.restaurant"})
    @NullMarked
    List<Dish> findAll();

    @EntityGraph(attributePaths = {"category", "menus.restaurant"})
    List<Dish> findDishByName(String name);

    @EntityGraph(attributePaths = {"category", "menus.restaurant"})
    List<Dish> findDishByPrice(double price);
}
