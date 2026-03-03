package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Order;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"dishes", "customer"})
    @Override
    @NullMarked
    Optional<Order> findById(Long id);

    @EntityGraph(attributePaths = {"dishes", "customer"})
    @Override
    @NullMarked
    List<Order> findAll();
}
