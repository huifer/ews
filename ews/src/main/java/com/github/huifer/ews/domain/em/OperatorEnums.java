package com.github.huifer.ews.domain.em;

public enum OperatorEnums {
	EQ("==", "等于", 0),
	NEQ("!=", "不等于", 1),
	GTE(">=", "大于等于", 2),
	GT(">", "大于", 3),
	LT("<", "小于", 4),
	LTE("<=", "小于等于", 5);
	private final String code;
	private final String name;
	private final int id;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	OperatorEnums(String code, String name, int id) {
		this.code = code;
		this.name = name;
		this.id = id;
	}

	public static OperatorEnums oc(int i) {
		for (OperatorEnums value : OperatorEnums.values()) {
			if (value.id == i) {
				return value;
			}
		}
		return null;
	}
}
