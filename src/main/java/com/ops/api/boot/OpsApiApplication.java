package com.ops.api.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(basePackages={"com.ops.api"})
public class OpsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpsApiApplication.class, args);
	}

}

