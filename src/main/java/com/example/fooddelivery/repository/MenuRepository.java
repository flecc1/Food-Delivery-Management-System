package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.entity.Menu;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @EntityGraph(attributePaths = {"restaurant", "dishes"})
    @NullMarked
    List<Menu> findMenuByName(String name);

    @EntityGraph(attributePaths = {"restaurant", "dishes"})
    @NullMarked
    List<Menu> findAllByRestaurantId(Long restaurantId);

    @EntityGraph(attributePaths = {"restaurant", "dishes"})
    @Override
    @NullMarked
    List<Menu> findAll();

    @EntityGraph(attributePaths = {"restaurant", "dishes"})
    @NullMarked
    Optional<Menu> findWithRestaurantAndDishesById(Long id);
}
