package br.com.pilares.storage.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

@Configuration
public class AmazonConfig {
	
	 @Value("${aws.s3.key}")
	 private String key;
	 
	 @Value("${aws.s3.secret}")
	 private String secret;
	
	 @Bean
	 public AmazonS3 s3() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(key, secret);
		AmazonS3 s3 =  AmazonS3ClientBuilder.standard().withRegion("us-east-1").withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
		return s3;
	 }

}
