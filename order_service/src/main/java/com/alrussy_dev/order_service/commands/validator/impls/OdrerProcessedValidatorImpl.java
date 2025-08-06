package com.alrussy_dev.order_service.commands.validator.impls;

import com.alrussy_dev.order_service.commands.validator.OrderProcessedValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Primary
public class OdrerProcessedValidatorImpl implements OrderProcessedValidator {

    @Override
    public void validate() {}
    ;
}
