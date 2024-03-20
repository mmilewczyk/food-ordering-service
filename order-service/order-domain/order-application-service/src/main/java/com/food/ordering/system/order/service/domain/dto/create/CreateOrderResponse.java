package com.food.ordering.system.order.service.domain.dto.create;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.food.ordering.system.domain.valueobject.OrderStatus;

import lombok.Builder;

@Builder
public record CreateOrderResponse(@NotNull UUID orderTrackingId,
                                  @NotNull OrderStatus orderStatus,
                                  @NotNull String message) {
}
