package com.github.huifer.ews.service;

import com.github.huifer.ews.domain.db.RuleDetail;
import com.github.huifer.ews.domain.dto.ProcessDetail;

import java.util.List;

public abstract class AbstractHandler {

	/**
	 * 根据规则机械能值提取
	 *
	 * @param ruleDetail
	 * @param jsonData
	 * @return
	 */
	abstract Boolean resolveEval(RuleDetail ruleDetail, String jsonData);

	abstract void process(ProcessDetail processDetail, String jsonData);

	protected void proc(List<ProcessDetail> processDetails, String jsonData) {
		for (ProcessDetail processDetail : processDetails) {
			process(processDetail, jsonData);
		}
	}
}
