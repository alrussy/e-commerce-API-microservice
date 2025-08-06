package com.alrussy_dev.order_service.commands.validator.impls;

import com.alrussy_dev.order_service.commands.validator.OrderShippedValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Primary
public class OdrerShippedValidatorImpl implements OrderShippedValidator {

    @Override
    public void validate() {}
    ;
}
