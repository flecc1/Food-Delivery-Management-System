package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.menu.MenuCreateDto;
import com.example.fooddelivery.dto.menu.MenuDto;
import com.example.fooddelivery.entity.Category;
import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.entity.Menu;
import com.example.fooddelivery.entity.Restaurant;
import com.example.fooddelivery.exception.CategoryNotFoundException;
import com.example.fooddelivery.exception.DishHasAnotherRestaurantException;
import com.example.fooddelivery.exception.DishNotFoundException;
import com.example.fooddelivery.exception.MenuHasDishesException;
import com.example.fooddelivery.exception.MenuNotFoundException;
import com.example.fooddelivery.exception.RestaurantNotFoundException;
import com.example.fooddelivery.mapper.DishMapper;
import com.example.fooddelivery.mapper.MenuMapper;
import com.example.fooddelivery.repository.CategoryRepository;
import com.example.fooddelivery.repository.DishRepository;
import com.example.fooddelivery.repository.MenuRepository;
import com.example.fooddelivery.repository.OrderRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private static final String MENU_NOT_FOUND_MSG = "Menu not found with id: ";
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final DishMapper dishMapper;
    private final CategoryRepository categoryRepository;

    public MenuDto findById(Long id) {
        log.debug("try find menu with id: {}", id);
        MenuDto menuDto = menuMapper.toDto(menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND_MSG + id)));
        log.info("menu found with id: {} successfully", id);
        return menuDto;
    }

    public Page<MenuDto> findAllMenus(Pageable pageable) {
        log.debug("try find all menus");
        Page<MenuDto> menuPage = menuRepository.findAll(pageable)
                .map(menuMapper::toDto);
        log.info("all menus found successfully");
        return menuPage;

    }

    public Page<MenuDto> findMenuByName(String name, Pageable pageable) {
        log.debug("try find menu by name: '{}'", name);
        Page<MenuDto> menuPage = menuRepository.findMenuByName(name, pageable).map(menuMapper::toDto);
        log.info("menu found with name: {} successfully", name);
        return menuPage;
    }

    public Page<MenuDto> findMenuByRestaurantId(Long restaurantId, Pageable pageable) {
        log.debug("try find menu by restaurant id: '{}'", restaurantId);
        if (!restaurantRepository.existsById(restaurantId)) {
            log.warn("restaurant not found with id: {}", restaurantId);
            throw new RestaurantNotFoundException(MENU_NOT_FOUND_MSG + restaurantId);
        }
        Page<MenuDto> menuDto = menuRepository.findAllByRestaurantId(restaurantId, pageable)
                .map(menuMapper::toDto);
        log.info("menu found with id: {} that exist in restaurant with id: {} successfully",
                restaurantId, restaurantId);
        return menuDto;
    }

    @Transactional
    public MenuDto addMenu(MenuCreateDto menuCreateDto) {
        log.debug("try to add menu with name: {}", menuCreateDto.getName());
        Menu menu = menuMapper.toEntity(menuCreateDto);
        menu.setActive(true);
        log.debug("try to find restaurant with id: {}", menuCreateDto.getRestaurantId());
        Restaurant restaurant = restaurantRepository.findById(menuCreateDto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: "
                        + menuCreateDto.getRestaurantId()));
        log.debug("restaurant found with id: {} successfully", menuCreateDto.getRestaurantId());
        menu.setRestaurant(restaurant);
        log.debug("check list of dishes for new menu");
        if (menuCreateDto.getDishesIds() != null) {
            log.debug("try to link {} dishes to menu", menuCreateDto.getDishesIds().size());
            List<Dish> dishes = dishRepository.findAllById(menuCreateDto.getDishesIds());
            if (!dishes
                    .stream()
                    .allMatch(dish -> dish.getMenu().getRestaurant().getId().equals(restaurant.getId()))) {
                throw new IllegalArgumentException("you can add dishes only from restaurant " + restaurant.getName());
            }
            dishes.forEach(dish -> dish.setMenu(menu));
            menu.setDishes(dishes);
            log.debug("dishes set successfully");
        }
        Menu saved = menuRepository.save(menu);
        log.info("menu added with id: {} successfully", saved.getId());
        return menuMapper.toDto(saved);
    }

    @Transactional
    public MenuDto updateMenuById(Long id, MenuCreateDto menuCreateDto) {
        log.debug("try to update menu with id: {}", id);
        log.debug("try to find menu with id: {}", id);
        Menu exist = menuRepository.findWithRestaurantAndDishesById(id)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND_MSG + id));
        exist.setName(menuCreateDto.getName());
        exist.setDescription(menuCreateDto.getDescription());
        log.debug("try to set restaurant with id: {}", menuCreateDto.getRestaurantId());
        if (!menuCreateDto.getRestaurantId().equals(exist.getRestaurant().getId())) {
            Restaurant newRestaurant = restaurantRepository.findById(menuCreateDto.getRestaurantId())
                    .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: "
                            + menuCreateDto.getRestaurantId()));
            log.debug("restaurant with id: {} was found successfully", menuCreateDto.getRestaurantId());
            exist.setRestaurant(newRestaurant);
        }
        log.debug("check if list of dishes is empty in menu with id: {}, name: {}", id, menuCreateDto.getName());
        if (menuCreateDto.getDishesIds() != null) {
            List<Dish> updatedDishes = dishRepository.findAllById(menuCreateDto.getDishesIds());
            exist.setDishes(updatedDishes);
            log.debug("founded dishes set successfully");
        }
        Menu updated = menuRepository.save(exist);
        log.info("menu updated with id: {} successfully", id);
        return menuMapper.toDto(updated);
    }

    @Transactional
    public MenuDto addDishToMenu(Long menuId, Long dishId) {
        log.debug("try to add dish to exist menu with id: {}", menuId);
        log.debug("try to find menu by id: {}", menuId);
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND_MSG + menuId));
        log.debug("try to find dish by id: {}", dishId);
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + dishId));
        log.debug("dish found with id: {} successfully", dishId);
        if (dish.getMenu().getRestaurant().getId().equals(menu.getRestaurant().getId())) {
            dish.setMenu(menu);
        } else {
            throw new DishHasAnotherRestaurantException("Dish with id " + dishId + " linked to another"
                    + " another restaurant " + dish.getMenu().getRestaurant().getName());
        }
        dishRepository.save(dish);
        log.info("dish with id: {} add to menu with id: {} successfully", dishId, menuId);
        return menuMapper.toDto(menu);
    }

    @Transactional
    public void deleteMenuById(Long id) {
        log.debug("check if menu with id: {} exist", id);
        if (!menuRepository.existsById(id)) {
            log.warn("delete failed menu with id: {} not found", id);
            throw new MenuNotFoundException(MENU_NOT_FOUND_MSG + id);
        }
        log.debug("check if menu connect with dishes");
        if (!orderRepository.findByMenuId(id).isEmpty()) {
            log.warn("delete failed menu with id: {} has dishes", id);
            throw new MenuHasDishesException("Menu with id " + id + "has dishes and cannot be delete");
        }
        menuRepository.deleteById(id);
        log.info("menu with id: {} deleted successfully", id);
    }

    @Transactional
    public MenuDto addDishesToMenu(Long menuId, List<DishCreateDto> dishList) {
        log.debug("try to add dishes(bulk) to exist menu with id: {}", menuId);
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND_MSG + menuId));


        List<Dish> dishes = dishList.stream()
                .map(dto -> {
                    if (!dto.getMenuId().equals(menu.getId())) {
                        throw new DishHasAnotherRestaurantException("you can add dishes only with "
                                + "menu id: " + menuId);
                    }

                    Dish dish = dishMapper.toEntity(dto);
                    if (dto.getCategoryId() != null) {
                        Category category = categoryRepository.findById(dto.getCategoryId())
                                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
                        dish.setCategory(category);
                    }
                    dish.setMenu(menu);
                    return dishRepository.saveAndFlush(dish);
                })
                .toList();
        menu.getDishes().addAll(dishes);
        return menuMapper.toDto(menu);
    }
}
