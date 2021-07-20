package com.github.huifer.ews.ctr;

import com.github.huifer.ews.entity.RuleEntity;
import com.github.huifer.ews.persistence.RuleEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleController {

	@Autowired
	private RuleEntityRepo ruleEntityRepo;


	@PostMapping("/add")
	public ResponseEntity<RuleEntity> add(
			@RequestBody RuleEntity entity
	) {
		RuleEntity save = ruleEntityRepo.save(entity);
		return ResponseEntity.ok(save);
	}

}
