package com.tus.orderServiceA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class OrderServiceAApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceAApplication.class, args);
	}

}
