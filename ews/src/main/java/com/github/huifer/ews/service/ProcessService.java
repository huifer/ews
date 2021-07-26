package com.github.huifer.ews.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.huifer.ews.domain.db.Process;
import com.github.huifer.ews.domain.db.ProcessFalse;
import com.github.huifer.ews.domain.db.ProcessTrue;
import com.github.huifer.ews.domain.dto.ActionFull;
import com.github.huifer.ews.domain.dto.ProcessDetail;
import com.github.huifer.ews.domain.dto.ProcessFull;
import com.github.huifer.ews.domain.req.CreateProcessParamReq;
import com.github.huifer.ews.mapper.ProcessFalseMapper;
import com.github.huifer.ews.mapper.ProcessMapper;
import com.github.huifer.ews.mapper.ProcessTrueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessService {
	@Autowired
	private ProcessMapper processMapper;
	@Autowired
	private ProcessTrueMapper processTrueMapper;
	@Autowired
	private ProcessFalseMapper processFalseMapper;
	@Autowired
	private ActionService actionService;

	@Transactional
	public boolean save(CreateProcessParamReq paramReq) {
		Process process = paramReq.getProcess();

		int i = this.processMapper.insertOrUpdateSelective(process);
		if (i > 0) {
			Integer id = process.getId();

			List<Integer> trueActions = paramReq.getTrueActions();

			List<ProcessTrue> trues = new ArrayList<>();
			for (Integer trueAction : trueActions) {
				ProcessTrue processTrue = new ProcessTrue();
				processTrue.setProcessId(id);
				processTrue.setActionId(trueAction);
				trues.add(processTrue);
			}
			this.processTrueMapper.batchInsert(trues);

			List<Integer> falseActions = paramReq.getFalseActions();
			List<ProcessFalse> falses = new ArrayList<>();
			for (Integer trueAction : falseActions) {
				ProcessFalse processFalse = new ProcessFalse();
				processFalse.setProcessId(id);
				processFalse.setActionId(trueAction);
				falses.add(processFalse);
			}
			processFalseMapper.batchInsert(falses);
			return true;
		}
		return false;
	}

	public List<ProcessFull> fulls(Integer scenesId) {
		QueryWrapper<Process> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(Process.COL_SCENES_ID, scenesId);
		List<Process> processes = this.processMapper.selectList(queryWrapper);
		List<ProcessFull> fulls = new ArrayList<>();
		for (Process process : processes) {
			fulls.add(full(process.getId()));
		}
		return fulls;
	}

	public ProcessFull full(Integer processId) {
		Process process = this.processMapper.selectById(processId);
		if (process != null) {
			ProcessFull processFull = new ProcessFull();
			processFull.setProcess(process);

			handlerWithTrueProcess(processId, process, processFull);
			handlerWithFalseProcess(processId, process, processFull);

			return processFull;

		}

		return null;
	}

	private void handlerWithFalseProcess(Integer processId, Process process, ProcessFull processFull) {
		QueryWrapper<ProcessFalse> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(ProcessFalse.COL_PROCESS_ID, processId);
		List<ProcessFalse> falses = this.processFalseMapper.selectList(queryWrapper);


		List<ProcessDetail> processTrues = new ArrayList();

		for (ProcessFalse aFals : falses) {
			ProcessDetail processDetail = new ProcessDetail();
			processDetail.setProcess(process);

			Integer actionId = aFals.getActionId();
			ActionFull actionFull = this.actionService.actionFull(actionId);
			processDetail.setActionFull(actionFull);
			processTrues.add(processDetail);
		}
		processFull.setProcessTrues(processTrues);
	}

	private void handlerWithTrueProcess(Integer processId, Process process, ProcessFull processFull) {
		QueryWrapper<ProcessTrue> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(ProcessTrue.COL_PROCESS_ID, processId);
		List<ProcessTrue> trues = this.processTrueMapper.selectList(queryWrapper);

		List<ProcessDetail> processTrues = new ArrayList();

		for (ProcessTrue aTrue : trues) {
			ProcessDetail processDetail = new ProcessDetail();
			processDetail.setProcess(process);

			Integer actionId = aTrue.getActionId();
			ActionFull actionFull = this.actionService.actionFull(actionId);
			processDetail.setActionFull(actionFull);
			processTrues.add(processDetail);
		}
		processFull.setProcessTrues(processTrues);
	}

}