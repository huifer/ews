package com.github.huifer.ews.persistence;

import com.github.huifer.ews.entity.RuleInfoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleInfoEntityRepo extends JpaRepository<RuleInfoEntity, Integer> {

	List<RuleInfoEntity> findByRuleId(Integer ruleId);
}
