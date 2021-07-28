package com.github.huifer.ews.service;

import com.github.huifer.ews.EwsApp;
import com.github.huifer.ews.domain.db.Action;
import com.github.huifer.ews.domain.db.ActionParam;
import com.github.huifer.ews.domain.req.CreateActionParamReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;

@SpringBootTest(classes = {EwsApp.class})
@WebAppConfiguration
class ActionServiceTest {

	@Autowired
	private ActionService actionService;

	@Test
	void save() {
		CreateActionParamReq createActionParamReq = new CreateActionParamReq();
		Action action = new Action();
		action.setExample("测试");
		action.setHttpMethod("POST_FORM");
		action.setScenesId(1);
		action.setUrl("http://localhost:9012/form");
		createActionParamReq.setAction(action);

		ArrayList<ActionParam> list = new ArrayList<>();
		ActionParam p1 = new ActionParam();
		p1.setDefaultValue("123");
		p1.setExpression("name");
		p1.setTarget("name");
		list.add(p1);

		createActionParamReq.setList(list);

		actionService.save(createActionParamReq);
	}

	@Test
	void query() {
	}

	@Test
	void actionFull() {
	}
}