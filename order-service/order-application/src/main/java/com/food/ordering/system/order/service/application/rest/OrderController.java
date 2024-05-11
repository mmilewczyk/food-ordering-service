package com.food.ordering.system.order.service.application.rest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {

	private final OrderApplicationService orderApplicationService;

	@PostMapping
	public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand command) {
		log.info("Create order for customer: {} at restaurant: {}", command.customerId(), command.restaurantId());
		CreateOrderResponse response = orderApplicationService.createOrder(command);
		log.info("Order created with tracking id: {}", response.orderTrackingId());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{trackingId}")
	public ResponseEntity<TrackOrderResponse> getOrderByTrackingId(@PathVariable UUID trackingId) {
		TrackOrderQuery trackOrderQuery = TrackOrderQuery.builder().orderTrackingId(trackingId).build();
		TrackOrderResponse response = orderApplicationService.trackOrder(trackOrderQuery);
		log.info("Returning order status with tracking id: {}", response.orderTrackingId());
		return ResponseEntity.ok(response);
	}
}
