package com.github.huifer.ews.domain.dto;

import com.github.huifer.ews.domain.db.Process;
import lombok.Data;

@Data
public class ProcessDetail {
	private Process process;
	private ActionFull actionFull;


}
