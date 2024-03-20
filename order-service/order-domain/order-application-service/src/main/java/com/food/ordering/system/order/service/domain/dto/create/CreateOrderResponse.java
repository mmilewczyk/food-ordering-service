package com.food.ordering.system.order.service.domain.dto.create;

import java.util.UUID;

import com.food.ordering.system.domain.valueobject.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateOrderResponse(@NotNull UUID orderTrackingId,
                                  @NotNull OrderStatus orderStatus,
                                  @NotNull String message) {
}
