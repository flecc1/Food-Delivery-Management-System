package com.example.fooddelivery.cache;

import org.springframework.data.domain.Sort;

public record OrderCacheKey(String lastName, int page, int size, Sort sort) {
}
