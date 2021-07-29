package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.req.CreateProcessParamReq;
import com.github.huifer.ews.domain.res.ResVO;
import com.github.huifer.ews.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process")
@CrossOrigin
public class ProcessCtr {
	@Autowired
	private ProcessService processService;

	@PostMapping("/add")
	public ResVO addProcess(@RequestBody CreateProcessParamReq paramReq) {
		return ResVO.ok(processService.save(paramReq));
	}

	@GetMapping("/list")
	public ResVO list() {
		return ResVO.ok(processService.list());
	}

	@GetMapping("/query")
	public ResVO queryProcess(
			Integer scenesId,
			String name, String description
	) {
		return ResVO.ok(processService.queryProcess(scenesId,
				name,
				description));
	}

	@GetMapping("/p_true")
	public ResVO p_true(Integer processId) {
		return ResVO.ok(this.processService.p_true(processId));
	}

	@GetMapping("/p_false")
	public ResVO p_false(Integer processId) {
		return ResVO.ok(this.processService.p_false(processId));
	}
}
