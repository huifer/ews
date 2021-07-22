package com.github.huifer.ews.persistence;

import com.github.huifer.ews.entity.UrlEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.Path;
import java.util.List;

public interface UrlEntityRepo extends JpaRepository<UrlEntity, Integer> {

	List<UrlEntity> findByRuleId(Integer ruleId);

	Page<UrlEntity> findAll(Specification<UrlEntity> specification, Pageable pageable);

}
