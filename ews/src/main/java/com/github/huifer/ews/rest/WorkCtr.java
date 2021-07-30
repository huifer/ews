package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.res.ResVO;
import com.github.huifer.ews.service.HandlerScenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work")
public class WorkCtr {
	@Autowired
	private HandlerScenesService handlerScenesService;

	@PostMapping("/scenes")
	public ResVO scenes(
			String json,
			Integer scId
	) {
		this.handlerScenesService.work(scId, json);
		return ResVO.ok(true);
	}
}
