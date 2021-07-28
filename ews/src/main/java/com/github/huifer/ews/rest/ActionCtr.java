package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.req.CreateActionParamReq;
import com.github.huifer.ews.domain.res.ResVO;
import com.github.huifer.ews.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action")
@CrossOrigin
public class ActionCtr {
	@Autowired
	private ActionService actionService;

	@PostMapping("/add")
	public ResVO actionAdd(@RequestBody CreateActionParamReq param) {
		boolean save = actionService.save(param);
		return ResVO.ok(save);
	}

	@GetMapping("/query")
	public ResVO actionQuery(
			String scenesName,
			String httpMethod,
			String url,
			int pageSize,
			int pageNumber
	) {
		return ResVO.ok(this.actionService.query(
				scenesName,
				httpMethod,
				url,
				pageSize,
				pageNumber
		));
	}
}
