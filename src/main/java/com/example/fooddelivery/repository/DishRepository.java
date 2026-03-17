package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Dish;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {

    @EntityGraph(attributePaths = {"category", "menu", "menu.restaurant"})
    @NullMarked
    Optional<Dish> findWithCategoryAndMenuById(Long id);

    @Override
    @EntityGraph(attributePaths = {"category", "menu", "menu.restaurant"})
    @NullMarked
    Page<Dish> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"category", "menu", "menu.restaurant"})
    Page<Dish> findDishByName(String name, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "menu", "menu.restaurant"})
    Page<Dish> findDishByPrice(double price, Pageable pageable);
}