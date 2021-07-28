package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.db.RuleDetail;
import com.github.huifer.ews.domain.res.ResVO;
import com.github.huifer.ews.service.RuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rule")
@CrossOrigin
public class RuleDetailCtr {
	@Autowired
	private RuleDetailService ruleDetailService;

	@PostMapping("/add")
	public ResVO addRule(@RequestBody RuleDetail ruleDetail) {
		return ResVO.ok(this.ruleDetailService.saveOrUpdate(ruleDetail));
	}

	@GetMapping("/query")
	public ResVO queryRule(String sceneName, Integer sceneId, String name, int pageSize, int pageNumber) {
		return ResVO.ok(this.ruleDetailService.query(
				sceneName,
				sceneId,
				name,
				pageSize,
				pageNumber
		));
	}
}
