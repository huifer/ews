package com.github.huifer.ews.ctr;

import com.github.huifer.ews.req.JsonBody;
import com.github.huifer.ews.util.JsonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
public class AboutJsonCtr {
	@PostMapping("/json_to_pro")
	private ResponseEntity jsonToPro(
			@RequestBody JsonBody json
	) throws Exception {
		Properties properties = JsonUtil.getProperties(json.getJson());
		return ResponseEntity.ok(properties);
	}
}
