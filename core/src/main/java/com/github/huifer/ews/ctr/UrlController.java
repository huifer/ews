package com.github.huifer.ews.ctr;

import com.github.huifer.ews.entity.ParamEntity;
import com.github.huifer.ews.entity.UrlEntity;
import com.github.huifer.ews.persistence.ParamEntityRepo;
import com.github.huifer.ews.persistence.UrlEntityRepo;
import com.github.huifer.ews.req.UrlAddReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UrlController {
	private final ParamEntityRepo paramEntityRepo;
	private final UrlEntityRepo urlEntityRepo;

	public UrlController(ParamEntityRepo paramEntityRepo, UrlEntityRepo urlEntityRepo) {
		this.paramEntityRepo = paramEntityRepo;
		this.urlEntityRepo = urlEntityRepo;
	}

	@PostMapping("/url/add")
	public ResponseEntity urlAdd(
			@RequestBody UrlAddReq req
	) {
		UrlEntity urlEntity = req.getUrlEntity();
		UrlEntity save = this.urlEntityRepo.save(urlEntity);

		List<ParamEntity> paramEntities = req.getParamEntities();
		paramEntities.forEach(paramEntity -> paramEntity.setUrlId(save.getId()));
		List<ParamEntity> paramEntities1 = this.paramEntityRepo.saveAll(paramEntities);

		UrlAddReq urlAddReq = new UrlAddReq();
		urlAddReq.setUrlEntity(save);
		urlAddReq.setParamEntities(paramEntities1);
		return ResponseEntity.ok(urlAddReq);
	}
}
