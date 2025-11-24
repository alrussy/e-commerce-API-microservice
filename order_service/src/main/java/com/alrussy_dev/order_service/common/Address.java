package com.alrussy_dev.order_service.common;

public record Address(
        String name,
        String phoneNumber,
        String country,
        String city,
        String district,
        String street,
        String houseNumber,
        String note) {}
