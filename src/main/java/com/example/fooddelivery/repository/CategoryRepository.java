package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Category;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @EntityGraph(attributePaths = {"dishes"})
    Page<Category> findCategoryByName(String name, Pageable pageable);

    @EntityGraph(attributePaths = {"dishes"})
    @NullMarked
    Optional<Category> findWithDishesById(Long id);

    @Override
    @EntityGraph(attributePaths = {"dishes"})
    @NullMarked
    Page<Category> findAll(Pageable pageable);
}
