package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@SpringBootApplication(exclude = ContextStackAutoConfiguration.class)
@Slf4j
public class SimpleQueueServiceApplication {

	@Autowired
	private QueueMessagingTemplate qTemplate;

	@Value("${cloud.aws.end-point.uri}")
	private String endpoint;

	public static void main(String[] args) {
		SpringApplication.run(SimpleQueueServiceApplication.class, args);
	}

	/*
	 * @URL: http://localhost:8080/sendMessage/Welcome to AWS Simple Queue Service
	 */
	@GetMapping(value = "/sendMessage/{msg}")
	public String sendMessage(@PathVariable String msg) {
		qTemplate.send(endpoint, MessageBuilder.withPayload(msg).build());
		String res = "<h1>Message Sent Successfully</h1>";
		return res;
	}
	
	@SqsListener("IJ028-shubham-queue1")
	public void readMessagesFromQueue(String msg) {
		log.info("Message From Queue : {}", msg);
	}

}
