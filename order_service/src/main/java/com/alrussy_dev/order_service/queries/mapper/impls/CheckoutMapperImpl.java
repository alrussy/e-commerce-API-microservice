package com.alrussy_dev.order_service.queries.mapper.impls;

import com.alrussy_dev.order_service.queries.mapper.CheckoutMapper;
import com.alrussy_dev.order_service.queries.mapper.LineProductMapper;
import com.alrussy_dev.order_service.queries.model.Checkout;
import com.alrussy_dev.order_service.queries.model.dto.CheckoutRequest;
import com.alrussy_dev.order_service.queries.model.dto.CheckoutResponse;
import com.alrussy_dev.order_service.queries.model.dto.LineProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Primary
public class CheckoutMapperImpl implements CheckoutMapper {

    private final LineProductMapper lineProductMapper;

    @Override
    public Checkout toEntity(CheckoutRequest request) {

        return Checkout.builder()
                .addressId(request.addressId())
                .deliveryType(request.deliveryType())
                .discount(request.discount())
                .paymentMethod(request.paymentMethod())
                .shippingFee(request.shippingFee())
                .taxFee(request.taxFee())
                .lineProducts(
                        request.lineProducts() != null
                                ? request.lineProducts().stream()
                                        .map(lineProductMapper::toEntity)
                                        .collect(Collectors.toList())
                                : null)
                .build();
    }

    @Override
    public CheckoutResponse fromEntity(Checkout entity) {

        var lineProducts = entity.getLineProducts() == null
                ? null
                : entity.getLineProducts().stream()
                        .map(lineProductMapper::fromEntity)
                        .toList();

        var subTotal = lineProducts.stream().mapToDouble(t -> t.lineTotal()).sum();
        var orderTotal =
                (subTotal - subTotal * entity.getDiscount() / 100) + entity.getShippingFee() + entity.getTaxFee();
        return new CheckoutResponse(
                entity.getAddressId(),
                entity.getDiscount(),
                entity.getShippingFee(),
                entity.getTaxFee(),
                entity.getPaymentMethod(),
                entity.getDeliveryType(),
                subTotal,
                orderTotal,
                lineProducts);
    }

    @Override
    public CheckoutResponse fromEntity(Checkout entity, List<LineProductResponse> lineProducts) {
        var lines = entity.getLineProducts() == null
                ? null
                : entity.getLineProducts().stream()
                        .map(lineProductMapper::fromEntity)
                        .toList();
        var subTotal = lines.stream().mapToDouble(t -> t.lineTotal()).sum();
        var orderTotal = (subTotal - subTotal * entity.getDiscount() / 100) + entity.getShippingFee();
        return new CheckoutResponse(
                entity.getAddressId(),
                entity.getDiscount(),
                entity.getShippingFee(),
                entity.getTaxFee(),
                entity.getPaymentMethod(),
                entity.getDeliveryType(),
                subTotal,
                orderTotal,
                lineProducts == null ? null : lineProducts);
    }
}
