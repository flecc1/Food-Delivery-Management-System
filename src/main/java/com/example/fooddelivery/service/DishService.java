package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.entity.Category;
import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.entity.Menu;
import com.example.fooddelivery.exception.*;
import com.example.fooddelivery.mapper.DishMapper;
import com.example.fooddelivery.repository.CategoryRepository;
import com.example.fooddelivery.repository.DishRepository;
import com.example.fooddelivery.repository.MenuRepository;
import com.example.fooddelivery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishService {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    public DishDto findDishById(Long id) {
        return dishRepository.findWithCategoryAndMenuById(id).map(dishMapper::toDto)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + id));
    }

    public List<DishDto> findDishByName(String name) {
        return dishRepository.findDishByName(name)
                .stream().map(dishMapper::toDto).toList();
    }

    public List<DishDto> findAllDishes() {
        return dishRepository.findAll().stream().map(dishMapper::toDto).toList();
    }

    public List<DishDto> findDishByPrice(double price) {
        return dishRepository.findDishByPrice(price)
                .stream().map(dishMapper::toDto).toList();
    }

    @Transactional
    public DishDto addDish(DishCreateDto dishCreateDto) {
        Dish dish = dishMapper.toEntity(dishCreateDto);
        Category category = categoryRepository.findById(dishCreateDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        Menu menu = menuRepository.findById(dishCreateDto.getMenuId())
                .orElseThrow(() -> new MenuNotFoundException("Menu not found with id: "
                        + dishCreateDto.getMenuId()));
        dish.setMenu(menu);
        dish.setCategory(category);
        return dishMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public DishDto updateDishById(Long id, DishCreateDto dishCreateDto) {
        Dish exist = dishRepository.findWithCategoryAndMenuById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + id));

        exist.setName(dishCreateDto.getName());
        exist.setPrice(dishCreateDto.getPrice());
        exist.setDescription(dishCreateDto.getDescription());

        Category category = categoryRepository.findById(dishCreateDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: "
                        + dishCreateDto.getCategoryId()));
        exist.setCategory(category);
        return dishMapper.toDto(dishRepository.save(exist));
    }

    @Transactional
    public void deleteDishById(Long id) {
        if (!dishRepository.existsById(id)) {
            throw new DishNotFoundException("Dish not found with id: " + id);
        }
        if (!orderRepository.findByDishId(id).isEmpty()) {
            throw new OrderHasDishesException("Order has dishes and cannot be delete");
        }
        dishRepository.deleteById(id);
    }
}
