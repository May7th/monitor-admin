package com.zy.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		//new  SpringApplicationBuilder(AdminApplication.class).run(args);
		SpringApplication.run(AdminApplication.class, args);
	}
}
