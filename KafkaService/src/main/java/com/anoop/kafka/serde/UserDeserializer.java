package com.anoop.kafka.serde;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.anoop.kafka.avro.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDeserializer implements Deserializer<User> {

	@Override
	public void close() {

	}

	@Override
	public void configure(Map arg0, boolean arg1) {

	}

	@Override
	public User deserialize(String arg0, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		User user = null;
		try {
			user = mapper.readValue(arg1, User.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return user;
	}
}
