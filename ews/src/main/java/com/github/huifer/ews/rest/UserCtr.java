package com.github.huifer.ews.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserCtr {
	@PostMapping("/login")
	public ResponseEntity login() {
		Map<String, String> map = new HashMap<>();
		map.put("code", "2000");
		map.put("token", "admin-token");
		return ResponseEntity.ok(map);
	}

	@GetMapping("/info")
	public ResponseEntity info() {
		Map<String, String> map = new HashMap<>();
		map.put("roles", "1");
		map.put("introduction", "hhh");
		map.put("avatar", "1");
		map.put("name", "admin");
		return ResponseEntity.ok(map);

	}
}
