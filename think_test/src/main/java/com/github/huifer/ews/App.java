package com.github.huifer.ews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class App {
	@Autowired
	private RestTemplateBuilder builder;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return builder.build();
	}
}
