package com.github.huifer.ews.service;

import com.github.huifer.ews.domain.db.RuleDetail;

public abstract class AbstractHandler {

	/**
	 * 根据规则机械能值提取
	 * @param ruleDetail
	 * @param jsonData
	 * @return
	 */
	abstract Boolean resolveEval(RuleDetail ruleDetail, String jsonData);
}
