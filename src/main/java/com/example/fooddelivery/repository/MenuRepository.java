package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Menu;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @EntityGraph(attributePaths = {"restaurant", "dishes", "dishes.category"})
    @NullMarked
    Page<Menu> findMenuByName(String name, Pageable pageable);

    @EntityGraph(attributePaths = {"restaurant", "dishes", "dishes.category"})
    @NullMarked
    Page<Menu> findAllByRestaurantId(Long restaurantId,  Pageable pageable);

    @EntityGraph(attributePaths = {"restaurant", "dishes", "dishes.category"})
    @Override
    @NullMarked
    Page<Menu> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"restaurant", "dishes", "dishes.category"})
    @NullMarked
    Optional<Menu> findWithRestaurantAndDishesById(Long id);
}
