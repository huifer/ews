package com.github.huifer.ews.domain.res;

import lombok.Data;

@Data
public class ResVO {
	private Integer code;
	private Object data;

	public static ResVO ok(Object o) {
		ResVO resVO = new ResVO();
		resVO.setCode(20000);
		resVO.setData(o);
		return resVO;
	}
	public static ResVO error(Object o) {
		ResVO resVO = new ResVO();
		resVO.setCode(50000);
		resVO.setData(o);
		return resVO;
	}
}
