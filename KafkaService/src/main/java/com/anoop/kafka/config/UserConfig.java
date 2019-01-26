package com.anoop.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.anoop.kafka.avro.model.User;
import com.anoop.kafka.serde.UserDeserializer;
import com.anoop.kafka.serde.UserSerializer;

@Configuration
public class UserConfig {

	/*
	 * Producer Configuration
	 */
	@Bean
	public ProducerFactory<String, User> userProducerFactory() {
		return new DefaultKafkaProducerFactory<>(userProducerConfigs());
	}

	@Bean
	public Map<String, Object> userProducerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UserSerializer.class);
		// props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
		// AvroUserSerializer.class);

		/* To use Schema Registry */
		// props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
		// KafkaAvroSerializer.class);
		// props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
		// "http://localhost:8081");
		return props;
	}

	@Bean(name = "kafkaUserTemplate")
	public KafkaTemplate<String, User> kafkaTemplate() {
		return new KafkaTemplate<>(userProducerFactory());
	}

	/*
	 * Consumer Configuration
	 */
	@Bean
	ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<Integer, String> userConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(userConsumerConfigs());
	}

	@Bean
	public Map<String, Object> userConsumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "users");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserDeserializer.class);
		// props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
		// AvroUserDeserializer.class);

		/* To use Schema Registry */
		// props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
		// KafkaAvroDeserializer.class);
		// props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
		// "http://localhost:8081");
		return props;
	}

}
