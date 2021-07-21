package com.github.huifer.ews.ctr;

import com.github.huifer.ews.entity.ParamEntity;
import com.github.huifer.ews.entity.RuleInfoEntity;
import com.github.huifer.ews.entity.UrlEntity;
import com.github.huifer.ews.persistence.ParamEntityRepo;
import com.github.huifer.ews.persistence.RuleInfoEntityRepo;
import com.github.huifer.ews.persistence.UrlEntityRepo;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

@RestController
@RequestMapping("/rco")
public class RcoController {

	private static final Logger log = LoggerFactory.getLogger(RcoController.class);
	private final RuleInfoEntityRepo ruleInfoEntityRepo;
	private final UrlEntityRepo urlEntityRepo;
	private final ParamEntityRepo paramEntityRepo;
	private final RestTemplate restTemplate;
	ExpressionParser parser = new SpelExpressionParser();
	EvaluationContext context = new StandardEvaluationContext();


	public RcoController(RuleInfoEntityRepo ruleInfoEntityRepo, UrlEntityRepo urlEntityRepo, ParamEntityRepo paramEntityRepo, RestTemplate restTemplate) {
		this.ruleInfoEntityRepo = ruleInfoEntityRepo;
		this.urlEntityRepo = urlEntityRepo;
		this.paramEntityRepo = paramEntityRepo;
		this.restTemplate = restTemplate;
	}

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
		assert value != null;
		if (value) {
			// 3. 查询对应的url
			List<UrlEntity> urlEntities = this.urlEntityRepo.findByRuleId(ruleId);

			for (UrlEntity urlEntity : urlEntities) {
				Integer urlId = urlEntity.getId();
				List<ParamEntity> paramEntities = this.paramEntityRepo.findByUrlId(urlId);

				// 组装请求参数
				Properties params = new Properties();
				for (ParamEntity paramEntity : paramEntities) {
					String o = properties.getProperty(paramEntity.getSource());
					params.setProperty(paramEntity.getTarget(), o);
				}
				String paramsJson = new PropertiesToJsonConverter().convertToJson(params);

				// 4. 测试阶段全部POST_JSON模拟
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
				HttpEntity<String> request = new HttpEntity<>(paramsJson, headers);
				ResponseEntity<String> postForEntity = restTemplate.postForEntity(urlEntity.getUrl(), request, String.class);
				String body = postForEntity.getBody();
				System.out.println(body);
			}

		}
	}
}
