package com.github.huifer.ews.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogicalOperators {
	AND("&&", "并且"),
	OR("||", "或者"),
	;
	private final String code;
	private final String name;
}
