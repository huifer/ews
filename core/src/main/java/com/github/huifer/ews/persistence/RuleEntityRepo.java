package com.github.huifer.ews.persistence;

import com.github.huifer.ews.entity.RuleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleEntityRepo extends JpaRepository<RuleEntity, Integer> {

	Page<RuleEntity> findAll(Specification<RuleEntity> specification, Pageable pageable);
}
