package com.github.huifer.ews.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class ApiApp {

	public static void main(String[] args) {
		SpringApplication.run(ApiApp.class, args);
	}

	@PostMapping("/form")
	public Object form(String name, String age) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("age", age);
		return map;
	}

	@PostMapping("/json")
	public Object json(@RequestBody Object json) {
		return json;
	}
}
