package com.anoop.kafka;

import java.io.IOException;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.anoop.kafka.avro.model.User;

@Service
public class KafkaConsumer {

	private final Logger logger = LoggerFactory.getLogger(Producer.class);

	@KafkaListener(topics = "users", groupId = "users")
	public void consume(User user) throws IOException {
		logger.info(String.format("#### -> Consumed user -> %s", user));
	}
}