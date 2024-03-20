package com.food.ordering.system.order.service.domain;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
class OrderApplicationServiceImpl implements OrderApplicationService {

	private final OrderCreateCommandHandler orderCreateCommandHandler;
	private final OrderTrackCommandHandler orderTrackCommandHandler;

	@Override
	public CreateOrderResponse createOrder(CreateOrderCommand command) {
		return orderCreateCommandHandler.createOrder(command);
	}

	@Override
	public TrackOrderResponse trackOrder(TrackOrderQuery query) {
		return orderTrackCommandHandler.trackOrder(query);
	}
}
