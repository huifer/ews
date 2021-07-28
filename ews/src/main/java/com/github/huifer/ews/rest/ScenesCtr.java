package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.db.Scenes;
import com.github.huifer.ews.domain.res.ResVO;
import com.github.huifer.ews.service.ScenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scenes")
@CrossOrigin
public class ScenesCtr {

	@Autowired
	private ScenesService scenesService;

	@PostMapping("/add")
	public ResVO scenesAdd(@RequestBody Scenes scenes) {
		return ResVO.ok(this.scenesService.saveOrUpdate(scenes));
	}

	@GetMapping("/query")
	public ResVO scenesQuery(String name, String description, int pageSize, int pageNumber) {
		return ResVO.ok(this.scenesService.query(
				name,
				description,
				pageSize,
				pageNumber
		));
	}

	@GetMapping("/list")
	public ResVO list(){
		List<Scenes> scenes = this.scenesService.all();
		return ResVO.ok(scenes);
	}
}
