package com.github.huifer.ews.ctr;

import com.github.huifer.ews.entity.ParamEntity;
import com.github.huifer.ews.entity.RuleInfoEntity;
import com.github.huifer.ews.entity.UrlEntity;
import com.github.huifer.ews.persistence.ParamEntityRepo;
import com.github.huifer.ews.persistence.RuleInfoEntityRepo;
import com.github.huifer.ews.persistence.UrlEntityRepo;
import com.github.huifer.ews.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/rco")
public class RcoController {

	private static final Logger log = LoggerFactory.getLogger(RcoController.class);
	private final RuleInfoEntityRepo ruleInfoEntityRepo;
	private final UrlEntityRepo urlEntityRepo;
	private final ParamEntityRepo paramEntityRepo;
	private final RestTemplate restTemplate = new RestTemplate();
	ExpressionParser parser = new SpelExpressionParser();
	EvaluationContext context = new StandardEvaluationContext();


	public RcoController(RuleInfoEntityRepo ruleInfoEntityRepo, UrlEntityRepo urlEntityRepo, ParamEntityRepo paramEntityRepo) {
		this.ruleInfoEntityRepo = ruleInfoEntityRepo;
		this.urlEntityRepo = urlEntityRepo;
		this.paramEntityRepo = paramEntityRepo;
	}

	@PostMapping("/voic")
	public void voic(
			String json, Integer ruleId
	) throws Exception {

		Properties properties = JsonUtil.getProperties(json);
		// 1. ruleId ???????????????????????????
		List<RuleInfoEntity> byRuleId = ruleInfoEntityRepo.findByRuleId(ruleId);
		// 2. el ???????????????
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
			log.info("???????????????????????? = [{}] , ???????????? = [{}]", el, value);
			booleans.add(value);
		}
		Expression exp = parser.parseExpression(sb.toString());
		Boolean value = exp.getValue(context, Boolean.class);
		log.info("???????????????=[{}],?????????????????????=[{}]", sb, value);
		if (value) {
			// 3. ???????????????url
			List<UrlEntity> urlEntities = this.urlEntityRepo.findByRuleId(ruleId);

			for (UrlEntity urlEntity : urlEntities) {
				Integer urlId = urlEntity.getId();
				List<ParamEntity> paramEntities = this.paramEntityRepo.findByUrlId(urlId);

				// ??????????????????
				Properties params = new Properties();
				for (ParamEntity paramEntity : paramEntities) {
					String o = properties.getProperty(paramEntity.getSource());
					params.setProperty(paramEntity.getTarget(), o);
				}
				// 4. ??????????????????POST_JSON??????
				forPostJson(urlEntity.getUrl(), params);
			}

		}
	}

	protected void forPostJson(String url, Properties params) {
		String paramsJson = new PropertiesToJsonConverter().convertToJson(params);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> request = new HttpEntity<>(paramsJson, headers);
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, request, String.class);
		String body = postForEntity.getBody();
		System.out.println(body);
	}

	protected void forPostForm(String url, Properties params) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		params.forEach((k, v) -> {
			map.add(k.toString(), v.toString());
		});
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		System.out.println(response.getBody());
	}


}
