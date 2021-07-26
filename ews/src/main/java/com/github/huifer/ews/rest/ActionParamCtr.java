package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.db.ActionParam;
import com.github.huifer.ews.service.ActionParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action/param")
public class ActionParamCtr {
	@Autowired
	private ActionParamService actionParamService;

	@PostMapping("/add")
	public ResponseEntity addActionParam(
			@RequestBody ActionParam actionParam
	) {
		return ResponseEntity.ok(actionParam);
	}

	@GetMapping("/query")
	public ResponseEntity queryActionParam(Integer actionId, int pageSize, int pageNumber) {
		return ResponseEntity.ok(this.actionParamService.query(
				actionId,
				pageSize,
				pageNumber
		));
	}

}
