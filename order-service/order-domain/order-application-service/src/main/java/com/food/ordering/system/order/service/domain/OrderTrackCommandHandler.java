package com.food.ordering.system.order.service.domain;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderTrackCommandHandler {

	private final OrderDataMapper orderDataMapper;
	private final OrderRepository orderRepository;

	@Transactional(readOnly = true)
	public TrackOrderResponse trackOrder(TrackOrderQuery query) {
		Optional<Order> orderResult = orderRepository.findByTrackingId(new TrackingId(query.orderTrackingId()));
		if (orderResult.isEmpty()) {
			log.warn("Could not find order with tracking id: {}", query.orderTrackingId());
			throw new OrderNotFoundException("Could not find order with tracking id: " + query.orderTrackingId());
		}
		return orderDataMapper.orderToTrackOrderResponse(orderResult.get());
	}
}
