package com.food.ordering.system.order.service.domain.ports.input.service;

import javax.validation.Valid;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;

public interface OrderApplicationService {
	CreateOrderResponse createOrder(@Valid CreateOrderCommand command);
	TrackOrderResponse trackOrder(@Valid TrackOrderQuery query);
}
