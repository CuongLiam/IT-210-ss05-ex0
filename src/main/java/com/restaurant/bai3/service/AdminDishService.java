package com.restaurant.bai3.service;

import com.restaurant.common.Dish;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminDishService {

    private final List<Dish> dishes = List.of(
            new Dish(1, "Phở bò", 50000, true),
            new Dish(2, "Bún chả", 45000, false),
            new Dish(3, "Cơm tấm", 40000, true)
    );

    public Optional<Dish> findById(int id) {
        return dishes.stream()
                .filter(d -> d.getId() == id)
                .findFirst();
    }
}