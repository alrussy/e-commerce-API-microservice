package com.alrussy_dev.inventory_service.client;

public record Product(
        Long id, String name, String imageUrl, Category category, Department department, Brand brand, String about) {}
