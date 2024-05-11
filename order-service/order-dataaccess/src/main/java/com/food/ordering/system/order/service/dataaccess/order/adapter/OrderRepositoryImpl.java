package com.food.ordering.system.order.service.dataaccess.order.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity;
import com.food.ordering.system.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.food.ordering.system.order.service.dataaccess.order.repository.OrderJpaRepository;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

	private final OrderJpaRepository jpaRepository;
	private OrderDataAccessMapper mapper;

	@Override
	public Order save(Order order) {
		OrderEntity orderEntity = mapper.orderToOrderEntity(order);
		orderEntity = jpaRepository.save(orderEntity);
		return mapper.orderEntityToOrder(orderEntity);
	}

	@Override
	public Optional<Order> findByTrackingId(TrackingId trackingId) {
		return jpaRepository.findByTrackingId(trackingId.getValue())
				.map(mapper::orderEntityToOrder);
	}
}
