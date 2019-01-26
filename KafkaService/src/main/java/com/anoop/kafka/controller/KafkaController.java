package com.anoop.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anoop.kafka.KafkaProducer;
import com.anoop.kafka.avro.model.User;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

	@Autowired
	private KafkaProducer kafkaProducer;

	@RequestMapping(value = "/publishUser", method = RequestMethod.POST)
	public ResponseEntity<String> queueLot(@RequestBody User user) {

		if (StringUtils.isEmpty(user.getName())) {
			return new ResponseEntity<>("Name is Missing", HttpStatus.BAD_REQUEST);
		} else {
			System.out.println("Name - " + user.getName() + ", Age - " + user.getAge());
			this.kafkaProducer.sendMessage(user);
			return new ResponseEntity<>("Record Queued - ", HttpStatus.OK);
		}
	}
}