package com.github.huifer.ews.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogicalOperators {
  AND("AND", "并且"),
  OR("OR", "或者"),
  ;
  private final String code;
  private final String name;
}
