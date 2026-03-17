package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Order;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"dishes", "customer"})
    @NullMarked
    Optional<Order> findWithDishesAndCustomerById(Long id);

    @EntityGraph(attributePaths = {"dishes", "customer", "dishes.category", "dishes.menu", "dishes.menu.restaurant"})
    @Override
    @NullMarked
    Page<Order> findAll(Pageable pageable);

    @Query("""
        SELECT o FROM Order o 
        JOIN o.dishes d 
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

    @Query("""
    SELECT o FROM Order o
    JOIN FETCH o.customer c 
    JOIN FETCH o.dishes d
    JOIN FETCH d.menu m
    JOIN FETCH d.category cat
    JOIN FETCH m.restaurant
    WHERE c.lastName =:lastName 
    ORDER BY c.lastName ASC 
""")
    Page<Order> findByCustomerLastName(@Param("lastName") String lastName, Pageable pageable);
}
