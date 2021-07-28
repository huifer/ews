package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.db.ActionParam;
import com.github.huifer.ews.service.ActionParamService;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.huifer.ews.domain.res.ResVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action/param")
@CrossOrigin
public class ActionParamCtr {
	@Autowired
	private ActionParamService actionParamService;

	@PostMapping("/add")
	public ResVO addActionParam(
			@RequestBody ActionParam actionParam
	) {
		return ResVO.ok(actionParam);
	}

	@GetMapping("/query")
	public ResVO queryActionParam(Integer actionId, int pageSize, int pageNumber) {
		return ResVO.ok(this.actionParamService.query(
				actionId,
				pageSize,
				pageNumber
		));
	}

	@GetMapping("/list")
	public ResVO list(Integer actionId){
		return ResVO.ok(this.actionParamService.list(actionId));
	}
}
