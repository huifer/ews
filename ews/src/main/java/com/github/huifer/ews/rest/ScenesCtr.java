package com.github.huifer.ews.rest;

import com.github.huifer.ews.domain.db.Scenes;
import com.github.huifer.ews.service.ScenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scenes")
public class ScenesCtr {

	@Autowired
	private ScenesService scenesService;

	@PostMapping("/add")
	public ResponseEntity scenesAdd(@RequestBody Scenes scenes) {
		return ResponseEntity.ok(this.scenesService.saveOrUpdate(scenes));
	}

	@GetMapping("/query")
	public ResponseEntity scenesQuery(String name, String description, int pageSize, int pageNumber) {
		return ResponseEntity.ok(this.scenesService.query(
				name,
				description,
				pageSize,
				pageNumber
		));

	}
}
