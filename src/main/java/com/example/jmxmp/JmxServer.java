package com.example.jmxmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations={"classpath:application-Context.xml"})
@ComponentScan(basePackages ={"com.example.jmxmp"})
public class JmxServer {

	public static void main(String[] args) {
		SpringApplication.run(JmxServer.class, args);
	}
}

