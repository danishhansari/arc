package com.arc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class IssueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueServiceApplication.class, args);
	}

}
