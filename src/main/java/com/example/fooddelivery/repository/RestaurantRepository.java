package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantRepository {
    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepository() {
        Restaurant r1 = new Restaurant();
        r1.setId(1L);
        r1.setName("Pizza 21");
        r1.setAddress("st. Frunz, 10");
        r1.setCity("Minsk");
        r1.setRating(8.8);

        Restaurant r2 = new Restaurant();
        r2.setId(2L);
        r2.setName("Burger king");
        r2.setAddress("st. Nemiga, 10");
        r2.setCity("Minsk");
        r2.setRating(7.9);

        Restaurant r3 = new Restaurant();
        r3.setId(3L);
        r3.setName("KFC");
        r3.setAddress("st. Nemiga, 11");
        r3.setCity("Minsk");
        r3.setRating(9.1);

        Restaurant r4 = new Restaurant();
        r4.setId(4L);
        r4.setName("Papa Donner");
        r4.setAddress("st. Gikalo, 13");
        r4.setCity("Minsk");
        r4.setRating(5.9);

        restaurants.add(r1);
        restaurants.add(r2);
        restaurants.add(r3);
        restaurants.add(r4);
    }

    public List<Restaurant> findAll() {
        return restaurants;
    }

    public Optional<Restaurant> findById(Long id) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getId().equals(id))
                .findFirst();
    }

    public Restaurant addRestaurant(Restaurant newRestaurant) {
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public void deleteRestaurant(Restaurant restaurant) {
        restaurants.remove(restaurant);
    }

    public void deleteById(Long id) {
        for (Restaurant res : restaurants) {
            if (res.getId().equals(id)) {
                restaurants.remove(res);
                break;
            }
        }
    }


    public Restaurant updateRestaurant(Long id, Restaurant newRestaurant) {
        for (Restaurant res : restaurants) {
            if (res.getId().equals(id)) {
                res.setName(newRestaurant.getName());
                res.setAddress(newRestaurant.getAddress());
                res.setRating(newRestaurant.getRating());
                res.setCity(newRestaurant.getCity());
                return res;
            }
        }
        return null;
    }

    public List<Restaurant> findByName(String name) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getName().equals(name))
                .toList();
    }
}
