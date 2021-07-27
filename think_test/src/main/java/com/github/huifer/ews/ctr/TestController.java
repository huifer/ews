package com.github.huifer.ews.ctr;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {


	@PostMapping("/te")
	public Object te(@RequestBody Cm o) {
		return o;
	}


}
