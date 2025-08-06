package com.alrussy_dev.order_service.commands.validator;

@FunctionalInterface
public interface OrderShippedValidator extends Validation {
    void validate();
}
