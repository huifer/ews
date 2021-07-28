package com.github.huifer.ews.service;

import com.github.huifer.ews.EwsApp;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = {EwsApp.class})
@WebAppConfiguration
class HandlerScenesServiceTest {
	Gson gson = new Gson();
	@Autowired
	private HandlerScenesService handlerScenesService;

	@Test
	void work() {
		Map<String, String> map = new HashMap<>();
		map.put("age", "12");
		map.put("name", "zhangsan");
		handlerScenesService.work(1, gson.toJson(map));
	}
}