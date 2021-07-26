package com.github.huifer.ews.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.huifer.ews.domain.db.Action;
import com.github.huifer.ews.domain.db.ActionParam;
import com.github.huifer.ews.domain.dto.ActionFull;
import com.github.huifer.ews.domain.req.CreateActionParamReq;
import com.github.huifer.ews.domain.res.ActionVO;
import com.github.huifer.ews.mapper.ActionMapper;
import com.github.huifer.ews.mapper.ActionParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActionService {
	@Autowired
	private ActionMapper actionMapper;
	@Autowired
	private ActionParamMapper actionParamMapper;

	@Transactional
	public boolean save(CreateActionParamReq createActionParamReq) {
		Action action = createActionParamReq.getAction();
		int insert = this.actionMapper.insert(action);
		if (insert > 0) {
			int i = this.actionParamMapper.batchInsert(createActionParamReq.getList());
			return i > 0;
		}
		return false;
	}


	public IPage<ActionVO> query(String scenesName,
								 String httpMethod,
								 String url,
								 int pageSize, int pageNumber) {
		Page<ActionVO> objectPage =
				new Page<>(pageNumber, pageSize);

		IPage<ActionVO> page = actionMapper.query(
				scenesName,
				httpMethod,
				url,
				objectPage
		);

		return page;
	}


	/**
	 * 查询动作对象
	 */
	public ActionFull actionFull(Integer actionId) {
		Action action = this.actionMapper.selectById(actionId);

		if (action != null) {
			ActionFull actionFull = new ActionFull();
			actionFull.setAction(action);


			QueryWrapper<ActionParam> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq(ActionParam.COL_ACTION_ID, actionId);
			List<ActionParam> actionParams = actionParamMapper.selectList(queryWrapper);
			actionFull.setActionParams(actionParams);
			return actionFull;
		}

		return null;
	}
}
