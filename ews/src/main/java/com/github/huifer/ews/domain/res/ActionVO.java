package com.github.huifer.ews.domain.res;

import lombok.Data;

@Data
public class ActionVO {
	private Integer id;

	private Integer scenesId;

	private String httpMethod;

	private String url;

	private String example;

	private String scenesName;
}
