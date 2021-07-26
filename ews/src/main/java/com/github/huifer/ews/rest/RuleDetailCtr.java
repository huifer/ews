package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.db.RuleDetail;
import com.github.huifer.ews.service.RuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rule")
public class RuleDetailCtr {
	@Autowired
	private RuleDetailService ruleDetailService;

	@PostMapping("/add")
	public ResponseEntity addRule(@RequestBody RuleDetail ruleDetail) {
		return ResponseEntity.ok(this.ruleDetailService.saveOrUpdate(ruleDetail));
	}

	@GetMapping("/query")
	public ResponseEntity queryRule(Integer sceneId, String name, int pageSize, int pageNumber) {
		return ResponseEntity.ok(this.ruleDetailService.query(
				sceneId,
				name,
				pageSize,
				pageNumber
		));
	}
}
