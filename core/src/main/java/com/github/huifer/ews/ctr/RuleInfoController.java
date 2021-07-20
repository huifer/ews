package com.github.huifer.ews.ctr;

import com.github.huifer.ews.entity.RuleInfoEntity;
import com.github.huifer.ews.persistence.RuleEntityRepo;
import com.github.huifer.ews.persistence.RuleInfoEntityRepo;
import com.github.huifer.ews.req.RuleGroupAddReq;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleInfoController {

	private final RuleInfoEntityRepo ruleInfoEntityRepo;
	private final RuleEntityRepo ruleEntityRepo;

	public RuleInfoController(
			RuleInfoEntityRepo ruleInfoEntityRepo, RuleEntityRepo ruleEntityRepo) {
		this.ruleInfoEntityRepo = ruleInfoEntityRepo;
		this.ruleEntityRepo = ruleEntityRepo;
	}

	@PostMapping("/rule/info/add")
	public ResponseEntity<List<RuleInfoEntity>> ruleInfoAdd(
			@RequestBody RuleGroupAddReq ruleGroupAddReq
	) {
		Integer ruleId = ruleGroupAddReq.getRuleId();
		List<RuleInfoEntity> rules = ruleGroupAddReq.getRules();
		rules.forEach(rule -> rule.setRuleId(ruleId));
		List<RuleInfoEntity> ruleInfoEntities = this.ruleInfoEntityRepo.saveAll(rules);
		return ResponseEntity.ok(ruleInfoEntities);

	}
}
