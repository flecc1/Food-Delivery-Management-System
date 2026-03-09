package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.menu.MenuCreateDto;
import com.example.fooddelivery.dto.menu.MenuDto;
import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.entity.Menu;
import com.example.fooddelivery.entity.Restaurant;
import com.example.fooddelivery.exception.DishNotFoundException;
import com.example.fooddelivery.exception.MenuHasDishesException;
import com.example.fooddelivery.exception.MenuNotFoundException;
import com.example.fooddelivery.exception.RestaurantNotFoundException;
import com.example.fooddelivery.mapper.MenuMapper;
import com.example.fooddelivery.repository.DishRepository;
import com.example.fooddelivery.repository.MenuRepository;
import com.example.fooddelivery.repository.OrderRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private static final String MENU_NOT_FOUND_MSG = "Menu not found with id: ";
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;

    public MenuDto findById(Long id) {
        return menuMapper.toDto(menuRepository.findWithRestaurantAndDishesById(id)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND_MSG + id)));
    }

    public List<MenuDto> findAllMenus() {
        return menuRepository.findAll().stream().map(menuMapper::toDto).toList();
    }

    public List<MenuDto> findMenuByName(String name) {
        return menuRepository.findMenuByName(name).stream().map(menuMapper::toDto).toList();
    }

    public List<MenuDto> findMenuByRestaurantId(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantNotFoundException(MENU_NOT_FOUND_MSG + restaurantId);
        }
        return menuRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(menuMapper::toDto)
                .toList();
    }

    @Transactional
    public MenuDto addMenu(MenuCreateDto menuCreateDto) {
        Menu menu = menuMapper.toEntity(menuCreateDto);
        menu.setActive(true);
        Restaurant restaurant = restaurantRepository.findById(menuCreateDto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: "
                        + menuCreateDto.getRestaurantId()));
        menu.setRestaurant(restaurant);

        if (menuCreateDto.getDishesIds() != null) {
            List<Dish> dishes = dishRepository.findAllById(menuCreateDto.getDishesIds());
            dishes.forEach(dish -> dish.setMenu(menu));
            menu.setDishes(dishes);
        }
        return menuMapper.toDto(menuRepository.save(menu));
    }

    @Transactional
    public MenuDto updateMenuById(Long id, MenuCreateDto menuCreateDto) {
        Menu exist = menuRepository.findWithRestaurantAndDishesById(id)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND_MSG + id));
        exist.setName(menuCreateDto.getName());
        exist.setDescription(menuCreateDto.getDescription());

        if (!menuCreateDto.getRestaurantId().equals(exist.getRestaurant().getId())) {
            Restaurant newRestaurant = restaurantRepository.findById(menuCreateDto.getRestaurantId())
                    .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: "
                            + menuCreateDto.getRestaurantId()));
            exist.setRestaurant(newRestaurant);
        }
        if (menuCreateDto.getDishesIds() != null) {
            List<Dish> updatedDishes = dishRepository.findAllById(menuCreateDto.getDishesIds());
            exist.setDishes(updatedDishes);
        }
        return menuMapper.toDto(menuRepository.save(exist));
    }

    @Transactional
    public MenuDto addDishToMenu(Long menuId, Long dishId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND_MSG + menuId));

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + dishId));
        dish.setMenu(menu);
        dishRepository.save(dish);
        return menuMapper.toDto(menu);
    }

    @Transactional
    public void deleteMenuById(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new MenuNotFoundException(MENU_NOT_FOUND_MSG + id);
        }
        if (!orderRepository.findByMenuId(id).isEmpty()) {
            throw new MenuHasDishesException("Menu has dishes and cannot be delete");
        }
        menuRepository.deleteById(id);
    }
}
