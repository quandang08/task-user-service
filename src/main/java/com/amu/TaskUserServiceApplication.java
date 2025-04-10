package com.amu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TaskUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskUserServiceApplication.class, args);
	}

}
