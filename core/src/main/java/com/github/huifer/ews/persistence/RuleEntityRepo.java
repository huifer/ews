package com.github.huifer.ews.persistence;

import com.github.huifer.ews.entity.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleEntityRepo extends JpaRepository<RuleEntity, Integer> {

}
