package com.github.huifer.ews.req;

import com.github.huifer.ews.entity.RuleInfoEntity;
import lombok.Data;

import java.util.List;

@Data
public class RuleGroupAddReq {

	private List<RuleInfoEntity> rules;
	private String name;
	private Integer ruleId;

}
