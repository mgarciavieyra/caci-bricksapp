package com.caci.test.bricks.main.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.caci.test.bricks")
public class BricksApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BricksApplication.class, args);
	}
}
