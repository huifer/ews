package com.github.huifer.ews.persistence;

import com.github.huifer.ews.entity.UrlEntity;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlEntityRepo extends JpaRepository<UrlEntity, Integer> {

	List<UrlEntity> findByRuleId(Integer ruleId);
}
