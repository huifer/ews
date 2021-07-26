package com.github.huifer.ews.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.huifer.ews.domain.db.RuleDetail;
import com.github.huifer.ews.mapper.RuleDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleDetailService {
	@Autowired
	private RuleDetailMapper ruleDetailMapper;

	public RuleDetail saveOrUpdate(RuleDetail ruleDetail) {
		int i = this.ruleDetailMapper.insertOrUpdateSelective(ruleDetail);
		return ruleDetail;
	}

	public IPage<RuleDetail> query(Integer sceneId, String name, int pageSize, int pageNumber) {
		IPage<RuleDetail> page = new Page<>(pageNumber, pageSize);
		QueryWrapper<RuleDetail> objectQueryWrapper = new QueryWrapper<>();
		objectQueryWrapper.eq(RuleDetail.COL_SCENES_ID, sceneId);
		objectQueryWrapper.like(RuleDetail.COL_NAME, name);
		IPage<RuleDetail> ruleDetailIPage = ruleDetailMapper.selectPage(page, objectQueryWrapper);
		return ruleDetailIPage;
	}

	public RuleDetail byId(Integer ruleDetailId) {
		return this.ruleDetailMapper.selectById(ruleDetailId);
	}
}
