package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
public class AwsSQSConfig {

	@Value("${cloud.aws.credentials.secret-key}")
	private String awsSecretKey;

	@Value("${cloud.aws.credentials.access-key}")
	private String awsAccessKey;
	
	// inject in our controller or main application
	@Bean
	public QueueMessagingTemplate queueMessagingTemplate() {
		return new QueueMessagingTemplate(amazonSQSAsync());
	}
	
	@Bean
	@Primary
	public AmazonSQSAsync amazonSQSAsync() {
		return AmazonSQSAsyncClientBuilder.standard()
										  .withRegion(Regions.AP_SOUTH_1)
										  .withCredentials(new AWSStaticCredentialsProvider(
												  		   new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
										  .build();
	}
}
