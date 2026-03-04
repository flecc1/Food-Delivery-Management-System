package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Category;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @EntityGraph(attributePaths = {"dishes"})
    List<Category> findCategoryByName(String name);

    @EntityGraph(attributePaths = {"dishes"})
    @NullMarked
    Optional<Category> findWithDishesById(Long id);
    
    @Override
    @EntityGraph(attributePaths = {"dishes"})
    @NullMarked
    List<Category> findAll();
}
