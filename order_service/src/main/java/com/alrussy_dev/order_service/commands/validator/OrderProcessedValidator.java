package com.alrussy_dev.order_service.commands.validator;

@FunctionalInterface
public interface OrderProcessedValidator extends Validation {
    void validate();
}
