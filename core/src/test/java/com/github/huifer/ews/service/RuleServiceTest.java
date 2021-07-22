package com.github.huifer.ews.service;

import com.github.huifer.ews.entity.RuleInfoEntity;
import com.github.huifer.ews.operator.OperatorEnums;
import com.github.huifer.ews.req.RuleEvalReq;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
class RuleServiceTest {
	@Autowired
	private RuleService ruleService;

	@Test
	void saveRule() {
		RuleEvalReq ruleEvalReq = new RuleEvalReq();
		ruleEvalReq.setName("test_rule_1");
		ruleEvalReq.setEval("([1]||[2])||[3]");

		List<RuleInfoEntity> ruleInfoEntities = new ArrayList<>();
		RuleInfoEntity r1 = new RuleInfoEntity();
		r1.setRelValue("10");
		r1.setValueExpression("age");
		r1.setOperatorEnum(OperatorEnums.EQ);
		ruleInfoEntities.add(r1);
		RuleInfoEntity r2 = new RuleInfoEntity();
		r2.setRelValue("10");
		r2.setValueExpression("age");
		r2.setOperatorEnum(OperatorEnums.EQ);
		ruleInfoEntities.add(r2);

		RuleInfoEntity r3 = new RuleInfoEntity();
		r3.setRelValue("10");
		r3.setValueExpression("age");
		r3.setOperatorEnum(OperatorEnums.EQ);
		ruleInfoEntities.add(r3);
		ruleEvalReq.setRuleInfoEntities(ruleInfoEntities);
		Gson gson = new Gson();
		System.out.println(gson.toJson(ruleEvalReq));
	}
}