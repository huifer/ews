package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.req.CreateActionParamReq;
import com.github.huifer.ews.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action")
public class ActionCtr {
	@Autowired
	private ActionService actionService;

	@PostMapping("/add")
	public ResponseEntity actionAdd(@RequestBody CreateActionParamReq param) {
		boolean save = actionService.save(param);
		return ResponseEntity.ok(save);
	}

	@GetMapping("/query")
	public ResponseEntity actionQuery(
			String scenesName,
			String httpMethod,
			String url,
			int pageSize,
			int pageNumber
	) {
		return ResponseEntity.ok(this.actionService.query(
				scenesName,
				httpMethod,
				url,
				pageSize,
				pageNumber
		));
	}
}
