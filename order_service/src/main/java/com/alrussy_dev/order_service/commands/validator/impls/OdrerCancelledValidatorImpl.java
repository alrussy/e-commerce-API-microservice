package com.alrussy_dev.order_service.commands.validator.impls;

import com.alrussy_dev.order_service.commands.validator.OrderCancelledValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Primary
public class OdrerCancelledValidatorImpl implements OrderCancelledValidator {

    @Override
    public void validate() {}
    ;
}
