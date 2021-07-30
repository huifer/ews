package com.github.huifer.ews.service;

import com.github.huifer.ews.domain.db.Action;
import com.github.huifer.ews.domain.db.ActionParam;
import com.github.huifer.ews.domain.db.Process;
import com.github.huifer.ews.domain.db.RuleDetail;
import com.github.huifer.ews.domain.dto.ActionFull;
import com.github.huifer.ews.domain.dto.ProcessDetail;
import com.github.huifer.ews.domain.dto.ProcessFull;
import com.github.huifer.ews.domain.dto.ScenesFull;
import com.github.huifer.ews.domain.em.OperatorEnums;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HandlerScenesService extends AbstractHandler {

	private final RestTemplate restTemplate = new RestTemplate();
	ExpressionParser parser = new SpelExpressionParser();
	EvaluationContext context = new StandardEvaluationContext();
	@Autowired
	private ScenesService scenesService;
	@Autowired
	private RuleDetailService ruleDetailService;

	public void work(Integer sceneId, String jsonData) {

		log.info("当前处理场景id=[{}],处理数据=[{}]", sceneId, jsonData);
		ScenesFull full = this.scenesService.full(sceneId);
		if (full == null) {
			return;
		}
		List<ProcessFull> processFulls = full.getProcessFulls();

		for (ProcessFull processFull : processFulls) {
			handlerProcessFull(processFull, jsonData);
		}

	}

	private void handlerProcessFull(ProcessFull processFull, String jsonData) {
		Process process = processFull.getProcess();
		Boolean aBoolean = checkProcessExpression(process, jsonData);
		log.info("当前处理对象=[{}],处理结果=[{}]", process, aBoolean);
		if (aBoolean) {
			List<ProcessDetail> processDetails = processFull.getProcessTrues();
			proc(processDetails, jsonData);
		} else {
			List<ProcessDetail> processDetails = processFull.getProcessFalses();
			proc(processDetails, jsonData);
		}
	}

	void process(ProcessDetail processDetail, String jsonData) {
		ActionFull actionFull = processDetail.getActionFull();
		Action action = actionFull.getAction();
		String url = action.getUrl();
		String httpMethod = action.getHttpMethod();
		List<ActionParam> actionParams = actionFull.getActionParams();
		// 参数列表
		Properties params = new Properties();
		for (ActionParam actionParam : actionParams) {
			Object data = extractForJsonPath(jsonData, actionParam.getExpression());
			params.put(actionParam.getTarget(),
					data == null ?
							actionParam.getDefaultValue() : data.toString());
		}
		log.info("当前正在处理http请求=[{}],param=[{}],httpMethod=[{}]", url, params, httpMethod);
		sendHttpMessage(url, params, httpMethod);
	}

	protected void sendHttpMessage(String url, Properties params, String httpMethod) {
		try {

			switch (httpMethod) {
				case "GET":
					return;
				case "POST_FORM":
					forPostForm(url, params);
					return;
				case "POST_JSON":
					forPostJson(url, params);
					return;
			}
		} catch (Exception e) {
			log.error("HTTP接口访问失败,url=[{}],param=[{}],httpMethod=[{}]", url, params, httpMethod);
		}
	}

	private Object extractForJsonPath(String jsonData, String jsonPath) {
		DocumentContext parse = JsonPath.parse(jsonData);
		Object read = parse.read(jsonPath);
		return read;
	}

	private Boolean checkProcessExpression(Process process, String jsonData) {
		// 提取处理表达式中的rule_id
		List<Integer> ruleIds = extractRuleId(process);
		// 处理rule以及对应的处理结果
		Map<Integer, Boolean> ruleMap = handlerRulesResult(ruleIds, jsonData);
		// 提取为修改前的条件表达式进行替换
		String expression = process.getExpression();
		for (Integer ruleId : ruleIds) {
			expression = expression.replace(ruleId.toString(), ruleMap.get(ruleId).toString());
		}
		log.info("el表达式修正前=[{}],修正后=[{}]", process.getExpression(), expression);
		return elBoolean(expression);

	}

	protected void forPostJson(String url, Properties params) {
		String paramsJson = new PropertiesToJsonConverter().convertToJson(params);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> request = new HttpEntity<>(paramsJson, headers);
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, request, String.class);
		String body = postForEntity.getBody();
		log.info("接口返回值=[{}]", body);
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
		log.info("接口返回值=[{}]", response.getBody());
	}

	/**
	 * 处理规则
	 */
	private Map<Integer, Boolean> handlerRulesResult(List<Integer> ruleIds, String jsonData) {
		Map<Integer, Boolean> result = new HashMap<>();
		for (Integer ruleId : ruleIds) {
			result.put(ruleId, handlerRuleResult(ruleId, jsonData));
		}
		return result;
	}

	private Boolean handlerRuleResult(Integer ruleId, String jsonData) {
		RuleDetail ruleDetail = ruleDetailService.byId(ruleId);
		if (ruleDetail != null) {
			return resolveEval(ruleDetail, jsonData);
		}
		return false;
	}


	@Override
	Boolean resolveEval(RuleDetail ruleDetail, String jsonData) {
		// 规则名称
		String name = ruleDetail.getName();
		// 从json提取的表达式
		String expression = ruleDetail.getExpression();
		// 比较数据
		String comparisonValue = ruleDetail.getComparisonValue();
		// 运算符
		Integer operator = ruleDetail.getOperator();
		OperatorEnums oc = OperatorEnums.oc(operator);
		// 取值
		DocumentContext parse = JsonPath.parse(jsonData);
		Object read = parse.read(expression);
		String el = read + oc.getCode() + comparisonValue;
		return elBoolean(el);
	}

	private Boolean elBoolean(String el) {
		Expression exp = parser.parseExpression(el);
		return exp.getValue(context, Boolean.class);
	}

	/**
	 * 提取规则id
	 */
	private List<Integer> extractRuleId(Process process) {
		String expression = process.getExpression();

		List<String> ss = new ArrayList<String>();
		for (String sss : expression.replaceAll("[^0-9]", ",").split(",")) {
			if (sss.length() > 0) {
				ss.add(sss);
			}
		}
		List<Integer> collect = ss.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		return collect;
	}
}
