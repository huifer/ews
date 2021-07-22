package com.github.huifer.ews.ctr;

import com.github.huifer.ews.entity.ParamEntity;
import com.github.huifer.ews.entity.UrlEntity;
import com.github.huifer.ews.persistence.ParamEntityRepo;
import com.github.huifer.ews.persistence.UrlEntityRepo;
import com.github.huifer.ews.req.PageParam;
import com.github.huifer.ews.req.UrlAddReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

	@GetMapping("/url/query")
	public ResponseEntity urlQuery(String url, PageParam pageParam) {
		Specification<UrlEntity> specification = (Specification) (root, criteriaQuery, cb) -> {
			List<Predicate> predicates = new ArrayList<>(); //所有的断言
			if (StringUtils.isNotBlank(url)) {
				Predicate likeNickName = cb.like(root.get("url").as(String.class), url + "%");
				predicates.add(likeNickName);
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		Pageable pageable = PageRequest.of(pageParam.getPage() - 1, pageParam.getLimit());
		Page<UrlEntity> all = urlEntityRepo.findAll(specification, pageable);
		return ResponseEntity.ok(all);
	}

}
