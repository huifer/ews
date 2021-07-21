package com.github.huifer.ews.req;

import com.github.huifer.ews.entity.RuleInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvalReq {
	/**
	 * 1 && (2 ||3)
	 * 123数据从ruleInfoEntities中来
	 */
	private String eval;

	private List<RuleInfoEntity> ruleInfoEntities;
}
