package com.github.huifer.ews.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OperatorEnums {
	EQ("==", "等于"),
	NEQ("!=", "不等于"),
	GTE(">=", "大于等于"),
	GT(">", "大于"),
	LT("<", "小于"),
	LTE("<=", "小于等于");
	private final String code;
	private final String name;

}
