package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.res.ResVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserCtr {
	@PostMapping("/login")
	public ResVO login() {
		Map<String, String> map = new HashMap<>();
		map.put("token", "admin-token");
		return ResVO.ok(map);
	}

	@GetMapping("/info")
	public ResVO info() {
		Map<String, String> map = new HashMap<>();
		map.put("roles", "1");
		map.put("introduction", "hhh");
		map.put("avatar", "1");
		map.put("name", "admin");
		return ResVO.ok(map);
	}
}
