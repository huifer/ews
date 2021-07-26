package com.github.huifer.ews.domain.dto;

import com.github.huifer.ews.domain.db.Action;
import com.github.huifer.ews.domain.db.ActionParam;
import lombok.Data;

import java.util.List;

@Data
public class ActionFull {
	private Action action;
	private List<ActionParam> actionParams;
}
