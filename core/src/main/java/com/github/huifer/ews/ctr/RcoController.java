package com.github.huifer.ews.ctr;

import com.github.huifer.ews.entity.RuleInfoEntity;
import com.github.huifer.ews.persistence.RuleInfoEntityRepo;
import com.github.huifer.ews.util.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rco")
public class RcoController {

	private static final Logger log = LoggerFactory.getLogger(RcoController.class);
	ExpressionParser parser = new SpelExpressionParser();
	EvaluationContext context = new StandardEvaluationContext();
	@Autowired
	private RuleInfoEntityRepo ruleInfoEntityRepo;

	@PostMapping("/voic")
	public void voic(
			String json, Integer ruleId
	) throws Exception {

		Properties properties = JsonUtil.getProperties(json);
		// 1. ruleId 查询对应的规则详情
		List<RuleInfoEntity> byRuleId = ruleInfoEntityRepo.findByRuleId(ruleId);
		// 2. el 表达式组装
		// 排序保证最后一个没有逻辑运算符
		byRuleId.sort((o1, o2) -> o1.getOrderId().compareTo(o2.getOrderId()));
		List<Boolean> booleans = new ArrayList<>();
		StringBuilder sb = new StringBuilder(32);

		for (RuleInfoEntity ruleInfoEntity : byRuleId) {
			Object o = properties.get(ruleInfoEntity.getValueExpression());
			if (o == null) {
				booleans.add(false);
				sb.append("false " + "|| ");
			}
			String el = o.toString() + ruleInfoEntity.getOperatorEnum().getCode() + ruleInfoEntity
					.getRelValue();
			Expression exp = parser.parseExpression(el);
			Boolean value = exp.getValue(context, Boolean.class);
			log.info("当前处理的表达式 = [{}] , 处理结果 = [{}]", el, value);
			booleans.add(value);
			if (ruleInfoEntity.getLogicalOperator() != null) {
				sb.append(value + " " + ruleInfoEntity.getLogicalOperator().getCode() + " ");
			} else {
				sb.append(value + " ");
			}
		}
		Expression exp = parser.parseExpression(sb.toString());
		Boolean value = exp.getValue(context, Boolean.class);
		log.info("最终表达式=[{}],表达式处理结果=[{}]", sb, value);

	}
}
