package com.github.huifer.ews.req;

import com.github.huifer.ews.entity.RuleInfoEntity;
import java.util.List;
import lombok.Data;

@Data
public class RuleGroup {

  private List<RuleInfoEntity> rules;
  private String name;
  private String uid;
}
