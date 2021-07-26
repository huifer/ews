package com.github.huifer.ews.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.huifer.ews.domain.db.ActionParam;
import com.github.huifer.ews.mapper.ActionParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionParamService {
	@Autowired
	private ActionParamMapper actionParamMapper;

	public ActionParam save(ActionParam actionParam) {
		int i = actionParamMapper.insertOrUpdate(actionParam);
		return actionParam;
	}

	public IPage<ActionParam> query(Integer actionId, int pageSize, int pageNumber) {
		IPage<ActionParam> page = new Page<>(pageNumber, pageSize);
		QueryWrapper<ActionParam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(ActionParam.COL_ACTION_ID, actionId);
		return actionParamMapper.selectPage(page, queryWrapper);
	}
}
