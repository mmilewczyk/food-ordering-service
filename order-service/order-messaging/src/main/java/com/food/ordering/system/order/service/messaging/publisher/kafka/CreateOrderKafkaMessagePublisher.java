package com.food.ordering.system.order.service.messaging.publisher.kafka;

import org.springframework.stereotype.Component;

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderKafkaMessagePublisher implements OrderCreatedPaymentRequestMessagePublisher {

	private final OrderMessagingDataMapper orderMessagingDataMapper;
	private final OrderServiceConfigData orderServiceConfigData;
	private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
	private final OrderKafkaMessageHelper orderKafkaMessageHelper;

	@Override
	public void publish(OrderCreatedEvent domainEvent) {
		String orderId = domainEvent.getOrder().getId().getValue().toString();
		log.info("Received OrderCreatedEvent for order id: {}", orderId);
		try {
			PaymentRequestAvroModel paymentRequestAvroModel =
					orderMessagingDataMapper.orderCreatedEventToPaymentRequestAvroModel(domainEvent);

			kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
					orderId,
					paymentRequestAvroModel,
					orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(), paymentRequestAvroModel, orderId, paymentRequestAvroModel.getClass().getName()));
			log.info("PaymentRequestAvroModel sent to Kafka for order id: {}", paymentRequestAvroModel.getOrderId());
		} catch (Exception e) {
			log.error("Error while sending PaymentRequestAvroModel message to kafka with order id: {}, error: {}", orderId, e.getMessage());
		}
	}
}