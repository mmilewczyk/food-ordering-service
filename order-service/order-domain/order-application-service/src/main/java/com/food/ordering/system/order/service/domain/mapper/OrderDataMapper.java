package com.food.ordering.system.order.service.domain.mapper;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;

@Component
public class OrderDataMapper {

	public Restaurant createOrderCommandToRestaurant(CreateOrderCommand command) {
		return Restaurant.Builder.builder()
				.restaurantId(new RestaurantId(command.restaurantId()))
				.products(command.items().stream()
						.map(orderItem -> new Product(new ProductId(orderItem.productId())))
						.toList())
				.build();
	}

	public Order createOrderCommandToOrder(CreateOrderCommand command) {
		return Order.Builder.builder()
				.customerId(new CustomerId(command.customerId()))
				.restaurantId(new RestaurantId(command.restaurantId()))
				.deliveryAddress(orderAddressToStreetAddress(command.orderAddress()))
				.price(new Money(command.price()))
				.items(orderItemsToOrderItemEntities(command.items()))
				.build();
	}

	private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
		return new StreetAddress(UUID.randomUUID(), orderAddress.street(), orderAddress.postalCode(), orderAddress.city());
	}

	private List<OrderItem> orderItemsToOrderItemEntities(List<com.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems) {
		return orderItems.stream()
				.map(orderItem -> OrderItem.builder()
						.product(new Product(new ProductId(orderItem.productId())))
						.price(new Money(orderItem.price()))
						.quantity(orderItem.quantity())
						.subTotal(new Money(orderItem.subTotal()))
						.build())
				.toList();
	}

	public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
		return CreateOrderResponse.builder()
				.orderTrackingId(order.getTrackingId().getValue())
				.orderStatus(order.getOrderStatus())
				.message(message)
				.build();
	}

	public TrackOrderResponse orderToTrackOrderResponse(Order order) {
		return TrackOrderResponse.builder()
				.orderTrackingId(order.getTrackingId().getValue())
				.orderStatus(order.getOrderStatus())
				.failureMessages(order.getFailureMessages())
				.build();
	}
}
