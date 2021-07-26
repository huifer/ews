package com.github.huifer.ews.domain.dto;

import com.github.huifer.ews.domain.db.Process;
import lombok.Data;

import java.util.List;

@Data
public class ProcessFull {
	private Process process;
	private List<ProcessDetail> processFalses;
	private List<ProcessDetail> processTrues;
}
