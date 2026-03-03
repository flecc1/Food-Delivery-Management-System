package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findMenuByName(String name);

    List<Menu> findAllByRestaurantId(Long restaurantId);
}
