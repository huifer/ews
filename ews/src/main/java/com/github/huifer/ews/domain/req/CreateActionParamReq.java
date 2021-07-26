package com.github.huifer.ews.domain.req;

import com.github.huifer.ews.domain.db.Action;
import com.github.huifer.ews.domain.db.ActionParam;
import lombok.Data;

import java.util.List;

@Data
public class CreateActionParamReq {
	private Action action;

	private List<ActionParam> list;

}
