package com.food.ordering.system.payment.service.domain.event;

import java.time.ZonedDateTime;
import java.util.List;

import com.food.ordering.system.payment.service.domain.entity.Payment;

public class PaymentCancelledEvent extends PaymentEvent {
	public PaymentCancelledEvent(Payment payment, ZonedDateTime createdAt, List<String> failureMessages) {
		super(payment, createdAt, failureMessages);
	}
}
