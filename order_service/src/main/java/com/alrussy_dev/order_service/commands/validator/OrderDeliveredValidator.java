package com.alrussy_dev.order_service.commands.validator;

@FunctionalInterface
public interface OrderDeliveredValidator extends Validation {

    void validate();
}
