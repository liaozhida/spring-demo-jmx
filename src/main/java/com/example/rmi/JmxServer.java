package com.example.rmi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages ={"com.example.rmi"})
public class JmxServer {

	public static void main(String[] args) {
		SpringApplication.run(JmxServer.class, args);
	}
}

