package com.github.huifer.ews.domain.res;

import com.github.huifer.ews.domain.db.RuleDetail;
import lombok.Data;

@Data
public class RuleDetailVO extends RuleDetail {
	private String sceneName;
	private String opStr;

	public RuleDetailVO(String sceneName, String opStr) {
		this.sceneName = sceneName;
		this.opStr = opStr;
	}

	public RuleDetailVO(Integer id, Integer scenesId, String expression, String comparisonValue, Integer operator, String name, String sceneName, String opStr) {
		super(id, scenesId, expression, comparisonValue, operator, name);
		this.sceneName = sceneName;
		this.opStr = opStr;
	}

	public RuleDetailVO(String sceneName) {
		this.sceneName = sceneName;
	}

	public RuleDetailVO(Integer id, Integer scenesId, String expression, String comparisonValue, Integer operator, String name, String sceneName) {
		super(id, scenesId, expression, comparisonValue, operator, name);
		this.sceneName = sceneName;
	}
}
