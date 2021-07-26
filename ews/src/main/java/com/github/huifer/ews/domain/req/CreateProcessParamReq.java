package com.github.huifer.ews.domain.req;

import com.github.huifer.ews.domain.db.Process;
import lombok.Data;

import java.util.List;

@Data
public class CreateProcessParamReq {
	private Process  process;
	private List<Integer> trueActions;
	private List<Integer> falseActions;
}
