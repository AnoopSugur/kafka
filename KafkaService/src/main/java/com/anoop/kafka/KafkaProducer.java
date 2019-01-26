package com.anoop.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.anoop.kafka.avro.model.User;

@Service
public class KafkaProducer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
	private static final String USER = "users";

	@Autowired
	private KafkaTemplate<String, User> kafkaUserTemplate;

	public void sendMessage(User user) {
		logger.info(String.format("#### -> Publishing User -> %s", user));
		// Producer <String, User> producer = new KafkaProducer(properties());
		// producer.send(new ProducerRecord<String, User>("myTopic", user));
		// System.out.println("Message " + user.toString() + " sent !!");
		// producer.send(new ProducerRecord<>("my-topic", user));
		// producer.close();
		this.kafkaUserTemplate.send(USER, user);
	}

}