package com.github.huifer.ews.persistence;

import com.github.huifer.ews.entity.ParamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParamEntityRepo extends JpaRepository<ParamEntity, Integer> {
	List<ParamEntity> findByUrlId(Integer urlId);
	Page<ParamEntity> findAll(Specification<ParamEntity> specification, Pageable pageable);
}
