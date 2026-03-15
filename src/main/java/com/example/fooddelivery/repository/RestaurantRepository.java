package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Restaurant;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByName(String name);

    @Query(value = """
    SELECT DISTINCT r.* FROM restaurant r
    JOIN menu m ON r.id = m.restaurant_id
    JOIN dish d ON m.id = d.menu_id
    JOIN category c ON d.category_id = c.id
    WHERE c.name =:categoryName
""",  nativeQuery = true)
    List<Restaurant> findByDishCategory(@Param("categoryName") String categoryName);

    @Override
    @NullMarked
    Page<Restaurant> findAll(Pageable pageable);
}
