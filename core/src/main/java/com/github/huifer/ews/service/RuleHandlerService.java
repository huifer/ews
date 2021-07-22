package com.github.huifer.ews.service;

import com.github.huifer.ews.entity.ParamEntity;
import com.github.huifer.ews.entity.RuleEntity;
import com.github.huifer.ews.entity.RuleInfoEntity;
import com.github.huifer.ews.entity.UrlEntity;
import com.github.huifer.ews.persistence.ParamEntityRepo;
import com.github.huifer.ews.persistence.RuleEntityRepo;
import com.github.huifer.ews.persistence.RuleInfoEntityRepo;
import com.github.huifer.ews.persistence.UrlEntityRepo;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@Slf4j
public class RuleHandlerService extends AbstractRuleHandler {
	ExpressionParser parser = new SpelExpressionParser();
	EvaluationContext context = new StandardEvaluationContext();
	@Autowired
	private RuleInfoEntityRepo ruleInfoEntityRepo;
	@Autowired
	private RuleEntityRepo ruleEntityRepo;
	@Autowired
	private UrlEntityRepo urlEntityRepo;
	@Autowired
	private ParamEntityRepo paramEntityRepo;

	@Override
	boolean canHandle(String json, Integer ruleId) {

		Optional<RuleEntity> byId = ruleEntityRepo.findById(ruleId);
		if (byId.isPresent()) {
			DocumentContext jsonContext = JsonPath.parse(json);
			RuleEntity ruleEntity = byId.get();
			String eval = ruleEntity.getEval();
			// 循环解析数据值
			List<RuleInfoEntity> ruleInfoEntities = ruleInfoEntityRepo.findByRuleId(ruleId);
			List<Boolean> booleans = new ArrayList<>(ruleInfoEntities.size());
			ruleInfoEntities.forEach(ruleInfoEntity -> handlerRuleInfoEntity(jsonContext, booleans, ruleInfoEntity));
			log.info("el表达式处理集合=[{}]", booleans);
			String reps = calcRelEl(eval, booleans);
			Boolean value = resolveEval(reps);
			log.info("原始el表达式=[{}],替换后结果=[{}],表达式处理结果=[{}]", eval, reps, value);
			return value;
		}
		return false;
	}

	@Override
	void handlerJsonWithRuleId(String json, Integer ruleId) {
		List<UrlEntity> urlEntities = this.urlEntityRepo.findByRuleId(ruleId);
		for (UrlEntity urlEntity : urlEntities) {
			Integer urlId = urlEntity.getId();
			List<ParamEntity> paramEntities = this.paramEntityRepo.findByUrlId(urlId);
			DocumentContext jsonContext = JsonPath.parse(json);

			// 组装请求参数
			Properties params = new Properties();
			for (ParamEntity paramEntity : paramEntities) {
				Object sourceData = jsonContext.read(paramEntity.getSource());
				if (sourceData != null) {
					params.setProperty(paramEntity.getTarget(), sourceData.toString());
				} else {
					params.setProperty(paramEntity.getTarget(), paramEntity.getDefaultValue());
				}
			}
			sendHttpMessage(urlEntity.getUrl(), params, urlEntity.getHttpMethod());
		}

	}

	private String calcRelEl(String eval, List<Boolean> booleans) {
		// 最终的el表达式
		String reps = eval;
		for (int i = 0; i < booleans.size(); i++) {
			Boolean aBoolean = booleans.get(i);
			int index = i + 1;
			String rep = "[" + index + "]";
			reps = reps.replace(rep, aBoolean.toString());
		}
		return reps;
	}

	private void handlerRuleInfoEntity(DocumentContext jsonContext, List<Boolean> booleans, RuleInfoEntity ruleInfoEntity) {
		// 从json中提取数据
		Object jsonpathCreatorName = jsonContext.read(ruleInfoEntity.getValueExpression());
		if (jsonpathCreatorName instanceof Number || jsonpathCreatorName instanceof String) {
			String el = jsonpathCreatorName + ruleInfoEntity.getOperatorEnum().getCode() + ruleInfoEntity
					.getRelValue();
			Boolean value = resolveEval(el);
			log.info("当前处理el表达式=[{}],处理结果=[{}]", el, value);
			booleans.add(value);
		} else {
			booleans.add(false);
		}
	}

	/**
	 * 解析表达式
	 *
	 * @param reps
	 * @return
	 */
	private Boolean resolveEval(String reps) {
		Expression exp = parser.parseExpression(reps);
		return exp.getValue(context, Boolean.class);
	}


}
