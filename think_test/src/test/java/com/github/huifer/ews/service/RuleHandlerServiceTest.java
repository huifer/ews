package com.github.huifer.ews.service;

import com.github.huifer.ews.App;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {App.class})
class RuleHandlerServiceTest {
	@Autowired
	private RuleHandlerService ruleHandlerService;

	@Test
	void work() {
		ruleHandlerService.work("{\n" +
				"    \"username\": \"aaa\",\n" +
				"    \"age\": \"110\",\n" +
				"    \"role\": [\n" +
				"        {\n" +
				"            \"id\": 1,\n" +
				"            \"name\": \"zs\",\n" +
				"            \"age\": 30\n" +
				"        }\n" +
				"    ]\n" +
				"}", 5);
	}
}