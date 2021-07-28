package com.github.huifer.ews.service;

import com.github.huifer.ews.EwsApp;
import com.github.huifer.ews.domain.db.Process;
import com.github.huifer.ews.domain.req.CreateProcessParamReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;

@SpringBootTest(classes = {EwsApp.class})
@WebAppConfiguration
class ProcessServiceTest {
	@Autowired
	private ProcessService processService;

	@Test
	void save() {

		CreateProcessParamReq paramReq = new CreateProcessParamReq();
		Process process = new Process();
		process.setScenesId(1);
		process.setExpression("1");
		process.setName("测试");
		process.setDescription("测试");



		paramReq.setProcess(process);

		ArrayList<Integer> falseActions = new ArrayList<>();
		falseActions.add(4);
		paramReq.setFalseActions(falseActions);
		paramReq.setTrueActions(falseActions);

		processService.save(paramReq);
	}

	@Test
	void fulls() {
	}

	@Test
	void full() {
	}
}