package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.entity.Category;
import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.entity.Menu;
import com.example.fooddelivery.exception.CategoryNotFoundException;
import com.example.fooddelivery.exception.DishNotFoundException;
import com.example.fooddelivery.exception.MenuNotFoundException;
import com.example.fooddelivery.exception.OrderHasDishesException;
import com.example.fooddelivery.mapper.DishMapper;
import com.example.fooddelivery.repository.CategoryRepository;
import com.example.fooddelivery.repository.DishRepository;
import com.example.fooddelivery.repository.MenuRepository;
import com.example.fooddelivery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<DishDto> findDishByName(String name, Pageable pageable) {
        return dishRepository.findDishByName(name, pageable).map(dishMapper::toDto);
    }

    public Page<DishDto> findAllDishes(Pageable pageable) {
        return dishRepository.findAll(pageable).map(dishMapper::toDto);
    }

    public Page<DishDto> findDishByPrice(double price, Pageable pageable) {
        return dishRepository.findDishByPrice(price, pageable)
                .map(dishMapper::toDto);
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

        Menu menu = menuRepository.findById(dishCreateDto.getMenuId())
                .orElseThrow(() -> new MenuNotFoundException("Menu not found with id: "
                        + dishCreateDto.getMenuId()));
        exist.setMenu(menu);
        return dishMapper.toDto(dishRepository.save(exist));
    }

    @Transactional
    public void deleteDishById(Long id) {
        if (!dishRepository.existsById(id)) {
            throw new DishNotFoundException("Dish not found with id: " + id);
        }
        if (!orderRepository.findByDishId(id).isEmpty()) {
            throw new OrderHasDishesException("Order with id " + id + "has dishes and cannot be delete");
        }
        dishRepository.deleteById(id);
    }
}
