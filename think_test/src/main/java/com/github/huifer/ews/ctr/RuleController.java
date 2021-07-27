package com.github.huifer.ews.ctr;

import com.github.huifer.ews.entity.RuleEntity;
import com.github.huifer.ews.persistence.RuleEntityRepo;
import com.github.huifer.ews.req.PageParam;
import com.github.huifer.ews.req.RuleEvalReq;
import com.github.huifer.ews.service.RuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RuleController {
	@Autowired
	private RuleService ruleService;
	@Autowired
	private RuleEntityRepo ruleEntityRepo;

	@PostMapping("/rule/add")
	public ResponseEntity ruleAdd(@RequestBody RuleEvalReq ruleEvalReq) {
		boolean b = ruleService.saveRule(ruleEvalReq);
		return ResponseEntity.ok(b);
	}

	@GetMapping("/rule/query")
	public ResponseEntity ruleQuery(String name, PageParam pageParam) {
		Specification<RuleEntity> specification = (root, criteriaQuery, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNotBlank(name)) {
				Predicate likeNickName = cb.like(root.get("name").as(String.class), name + "%");
				predicates.add(likeNickName);
			}
			return cb.and(predicates.toArray(new Predicate[0]));

		};
		Pageable pageable = PageRequest.of(pageParam.getPage() - 1, pageParam.getLimit());
		Page<RuleEntity> all = ruleEntityRepo.findAll(specification, pageable);
		return ResponseEntity.ok(all);
	}
}
