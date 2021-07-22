package com.github.huifer.ews.service;

import com.github.huifer.ews.entity.RuleEntity;
import com.github.huifer.ews.entity.RuleInfoEntity;
import com.github.huifer.ews.persistence.RuleEntityRepo;
import com.github.huifer.ews.persistence.RuleInfoEntityRepo;
import com.github.huifer.ews.req.RuleEvalReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {

	@Autowired
	private RuleInfoEntityRepo ruleInfoEntityRepo;
	@Autowired
	private RuleEntityRepo ruleEntityRepo;

	public boolean saveRule(RuleEvalReq ruleEvalReq) {
		String name = ruleEvalReq.getName();
		String eval = ruleEvalReq.getEval();

		RuleEntity ruleEntity = new RuleEntity();
		ruleEntity.setEval(eval);
		ruleEntity.setName(name);

		RuleEntity save = ruleEntityRepo.save(ruleEntity);
		Integer ruleId = save.getId();


		List<RuleInfoEntity> ruleInfoEntities = ruleEvalReq.getRuleInfoEntities();
		ruleInfoEntities.forEach(s -> s.setRuleId(ruleId));
		ruleInfoEntityRepo.saveAll(ruleInfoEntities);
		return true;
	}
}
