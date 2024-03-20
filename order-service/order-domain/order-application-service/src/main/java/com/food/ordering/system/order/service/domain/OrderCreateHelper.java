package com.food.ordering.system.order.service.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateHelper {

	private final OrderDomainService orderDomainService;
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	private final RestaurantRepository restaurantRepository;
	private final OrderDataMapper orderDataMapper;

	@Transactional
	public OrderCreatedEvent persistOrder(CreateOrderCommand command) {
		checkCustomer(command.customerId());
		Restaurant restaurant = checkRestaurant(command);
		Order order = orderDataMapper.createOrderCommandToOrder(command);
		OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitializeOrder(order, restaurant);
		saveOrder(order);
		log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
		return orderCreatedEvent;
	}

	private void checkCustomer(UUID customerId) {
		Optional<Customer> customer = customerRepository.findCustomer(customerId);
		if (customer.isEmpty()) {
			log.warn("Could not find customer with id: {}", customerId);
			throw new OrderDomainException("Could not find customer with customer id: " + customerId);
		}
	}

	private Restaurant checkRestaurant(CreateOrderCommand command) {
		Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(command);
		Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
		if (optionalRestaurant.isEmpty()) {
			log.warn("Could not find restaurant with restaurant id: {}", command.restaurantId());
			throw new OrderDomainException("Could not find restaurant with restaurant id: " + command.restaurantId());
		}
		return optionalRestaurant.get();
	}

	private Order saveOrder(Order order) {
		Order orderResult = orderRepository.save(order);
		if (orderResult == null) {
			log.error("Could not save order!");
			throw new OrderDomainException("Could not save order!");
		}
		log.info("Order is saved with id: {}", orderResult.getId().getValue());
		return orderResult;
	}
}
