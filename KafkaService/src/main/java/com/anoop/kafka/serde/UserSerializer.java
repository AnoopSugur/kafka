package com.anoop.kafka.serde;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.anoop.kafka.avro.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserSerializer implements Serializer<User> {

	@Override
	public void configure(Map arg0, boolean arg1) {

	}

	@Override
	public byte[] serialize(String arg0, User arg1) {
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(arg1).getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	@Override
	public void close() {

	}

}