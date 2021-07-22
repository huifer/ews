package com.github.huifer.ews.ctr;

import com.github.huifer.ews.req.RuleEvalReq;
import com.github.huifer.ews.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleController {
	@Autowired
	private RuleService ruleService;

	@PostMapping("/rule/add")
	public ResponseEntity ruleAdd(@RequestBody RuleEvalReq ruleEvalReq) {
		boolean b = ruleService.saveRule(ruleEvalReq);
		return ResponseEntity.ok(b);
	}
}
