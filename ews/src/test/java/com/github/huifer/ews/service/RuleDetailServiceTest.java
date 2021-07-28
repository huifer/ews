package com.github.huifer.ews.service;

import com.github.huifer.ews.EwsApp;
import com.github.huifer.ews.domain.db.RuleDetail;
import com.github.huifer.ews.domain.em.OperatorEnums;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = {EwsApp.class})
@WebAppConfiguration
class RuleDetailServiceTest {
	@Autowired
	private RuleDetailService ruleDetailService;

	@Test
	void saveOrUpdate() {
		RuleDetail ruleDetail = new RuleDetail();

		ruleDetail.setExpression("age");
		ruleDetail.setComparisonValue("10");
		ruleDetail.setOperator(OperatorEnums.GT.getId());
		ruleDetailService.saveOrUpdate(ruleDetail);
	}

	@Test
	void query() {
	}

	@Test
	void byId() {
	}
}