package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Order;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"dishes", "customer"})
    @NullMarked
    Optional<Order> findWithDishesAndCustomerById(Long id);

    @EntityGraph(attributePaths = {"dishes", "customer", "dishes.category", "dishes.menu", "dishes.menu.restaurant"})
    @Override
    @NullMarked
    List<Order> findAll();

    @Query("""
SELECT o FROM Order o
        JOIN  o.dishes d
        JOIN d.menu m
        JOIN m.restaurant r
        WHERE r.id = :restaurantId
""")
    List<Order> getByRestaurantId(Long restaurantId);

    @Query("""
    SELECT o FROM Order o
    JOIN o.dishes d
    WHERE d.id = :dishId
""")
    List<Order> findByDishId(Long dishId);

    @Query("""
    SELECT o FROM Order o
    JOIN o.dishes d
    JOIN d.menu m
    WHERE m.id = :menuId
"""
    )
    List<Order> findByMenuId(Long menuId);
}
