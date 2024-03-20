package com.food.ordering.system.order.service.domain.dto.create;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateOrderCommand(@NotNull UUID customerId,
                                 @NotNull UUID restaurantId,
                                 @NotNull BigDecimal price,
                                 @NotNull List<OrderItem> items,
                                 @NotNull OrderAddress orderAddress) {
}
