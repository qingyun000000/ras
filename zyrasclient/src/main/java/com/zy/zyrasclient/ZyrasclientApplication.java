package com.zy.zyrasclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.zy"})
public class ZyrasclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZyrasclientApplication.class, args);
	}

}
