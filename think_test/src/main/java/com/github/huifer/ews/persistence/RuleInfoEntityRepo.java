package com.github.huifer.ews.persistence;

import com.github.huifer.ews.entity.RuleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleInfoEntityRepo extends JpaRepository<RuleInfoEntity, Integer> {

	List<RuleInfoEntity> findByRuleId(Integer ruleId);
}
