package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.menu.MenuCreateDto;
import com.example.fooddelivery.dto.menu.MenuDto;
import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.entity.Menu;
import com.example.fooddelivery.entity.Restaurant;
import com.example.fooddelivery.exception.MenuNotFoundException;
import com.example.fooddelivery.exception.RestaurantNotFoundException;
import com.example.fooddelivery.mapper.MenuMapper;
import com.example.fooddelivery.repository.DishRepository;
import com.example.fooddelivery.repository.MenuRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    public MenuDto findById(Long id) {
        return menuMapper.toDto(menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException("Menu not found with id: " + id)));
    }

    public List<MenuDto> findAllMenus() {
        return menuRepository.findAll().stream().map(menuMapper::toDto).toList();
    }

    public List<MenuDto> findMenuByName(String name) {
        return menuRepository.findMenuByName(name).stream().map(menuMapper::toDto).toList();
    }

    public List<MenuDto> findMenuByRestaurantId(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId);
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
        return menuMapper.toDto(menuRepository.save(menu));
    }

    @Transactional
    public MenuDto updateMenuById(Long id, MenuCreateDto menuCreateDto) {
        Menu exist = menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException("Menu not found with id: " + id));
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
    public void deleteMenuById(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new MenuNotFoundException("Menu not found with id: " + id);
        }
        menuRepository.deleteById(id);
    }
}
