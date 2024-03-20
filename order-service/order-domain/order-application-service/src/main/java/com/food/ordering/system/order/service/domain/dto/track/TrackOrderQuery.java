package com.food.ordering.system.order.service.domain.dto.track;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TrackOrderQuery(@NotNull UUID orderTrackingId) {
}
