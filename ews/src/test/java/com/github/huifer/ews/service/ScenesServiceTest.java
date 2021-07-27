package com.github.huifer.ews.service;

import com.github.huifer.ews.EwsApp;
import com.github.huifer.ews.domain.db.Scenes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = {EwsApp.class})
@WebAppConfiguration
class ScenesServiceTest {
	@Autowired
	private ScenesService scenesService;


	@Test
	void saveOrUpdate() {
		Scenes scenes = new Scenes();
		scenes.setName("场景1");
		scenes.setExampleJson("{}");
		scenes.setDescription("测试场景");

		scenesService.saveOrUpdate(scenes);
	}

	@Test
	void query() {
	}

	@Test
	void full() {
	}
}