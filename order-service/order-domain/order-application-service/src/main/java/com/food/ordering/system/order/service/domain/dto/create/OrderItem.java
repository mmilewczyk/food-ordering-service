package com.food.ordering.system.order.service.domain.dto.create;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Builder;

@Builder
public record OrderItem(@NotNull UUID productId,
                        @NotNull Integer quantity,
                        @NotNull BigDecimal price,
                        @NotNull BigDecimal subTotal) {
}
