package com.github.huifer.ews.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.huifer.ews.domain.db.Action;
import com.github.huifer.ews.domain.db.Process;
import com.github.huifer.ews.domain.db.ProcessFalse;
import com.github.huifer.ews.domain.db.ProcessTrue;
import com.github.huifer.ews.domain.dto.ActionFull;
import com.github.huifer.ews.domain.dto.ProcessDetail;
import com.github.huifer.ews.domain.dto.ProcessFull;
import com.github.huifer.ews.domain.req.CreateProcessParamReq;
import com.github.huifer.ews.domain.res.ProcessVO;
import com.github.huifer.ews.mapper.ActionMapper;
import com.github.huifer.ews.mapper.ProcessFalseMapper;
import com.github.huifer.ews.mapper.ProcessMapper;
import com.github.huifer.ews.mapper.ProcessTrueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	@Autowired
	private ActionMapper actionMapper;

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
			if (!CollectionUtils.isEmpty(trues)) {
				this.processTrueMapper.batchInsert(trues);
			}
			List<Integer> falseActions = paramReq.getFalseActions();
			List<ProcessFalse> falses = new ArrayList<>();
			for (Integer trueAction : falseActions) {
				ProcessFalse processFalse = new ProcessFalse();
				processFalse.setProcessId(id);
				processFalse.setActionId(trueAction);
				falses.add(processFalse);
			}
			if (!CollectionUtils.isEmpty(falses)) {
				processFalseMapper.batchInsert(falses);
			}
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
			ProcessFull full = full(process.getId());
			fulls.add(full);
		}
		return fulls;
	}

	public ProcessFull full(Integer processId) {
		Process process = this.processMapper.selectById(processId);
		if (process != null) {
			ProcessFull processFull = new ProcessFull();
			processFull.setProcess(process);

			List<ProcessDetail> processDetails = handlerWithTrueProcess(processId, process);
			processFull.setProcessTrues(processDetails);
			List<ProcessDetail> processDetails1 = handlerWithFalseProcess(processId, process);
			processFull.setProcessFalses(processDetails1);
			return processFull;

		}

		return null;
	}

	private List<ProcessDetail> handlerWithFalseProcess(Integer processId, Process process) {
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
		return processTrues;
	}

	private List<ProcessDetail> handlerWithTrueProcess(Integer processId, Process process) {
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
		return processTrues;
	}

	public List<Process> list() {
		QueryWrapper<Process> queryWrapper = new QueryWrapper<>();
		return this.processMapper.selectList(queryWrapper);
	}

	public List<ProcessVO> queryProcess(Integer scenesId, String name, String description) {
		return this.processMapper.queryPage(scenesId, name, description);
	}

	public List<Action> p_true(Integer processId) {
		QueryWrapper<ProcessTrue> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(ProcessTrue.COL_PROCESS_ID, processId);
		List<ProcessTrue> processTrues = this.processTrueMapper.selectList(queryWrapper);
		List<Integer> actionIds = processTrues.stream().map(ProcessTrue::getActionId).collect(Collectors.toList());
		List<Action> actions = actionMapper.selectBatchIds(actionIds);
		return actions;
	}

	public List<Action> p_false(Integer processId) {
		QueryWrapper<ProcessFalse> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(ProcessFalse.COL_PROCESS_ID, processId);
		List<ProcessFalse> processFalses = this.processFalseMapper.selectList(queryWrapper);
		List<Integer> actionIds = processFalses.stream().map(ProcessFalse::getActionId).collect(Collectors.toList());
		List<Action> actions = actionMapper.selectBatchIds(actionIds);
		return actions;

	}
}
