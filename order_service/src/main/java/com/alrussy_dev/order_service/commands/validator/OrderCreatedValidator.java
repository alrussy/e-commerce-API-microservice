package com.alrussy_dev.order_service.commands.validator;

import com.alrussy_dev.order_service.queries.model.Order;

@FunctionalInterface
public interface OrderCreatedValidator extends Validation {

    void validate(Order order);
}
