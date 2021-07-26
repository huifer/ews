package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.req.CreateProcessParamReq;
import com.github.huifer.ews.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
public class ProcessCtr {
	@Autowired
	private ProcessService processService;

	@PostMapping("/add")
	public ResponseEntity addProcess(CreateProcessParamReq paramReq) {
		return ResponseEntity.ok(processService.save(paramReq));
	}
}
