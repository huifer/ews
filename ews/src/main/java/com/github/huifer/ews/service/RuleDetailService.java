package com.github.huifer.ews.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.huifer.ews.domain.db.RuleDetail;
import com.github.huifer.ews.domain.db.Scenes;
import com.github.huifer.ews.domain.em.OperatorEnums;
import com.github.huifer.ews.domain.res.RuleDetailVO;
import com.github.huifer.ews.mapper.RuleDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleDetailService {
	@Autowired
	private RuleDetailMapper ruleDetailMapper;
	@Autowired
	private ScenesService scenesService;

	public RuleDetail saveOrUpdate(RuleDetail ruleDetail) {
		int i = this.ruleDetailMapper.insertOrUpdateSelective(ruleDetail);
		return ruleDetail;
	}

	public IPage<RuleDetail> query(String sceneName, Integer sceneId, String name, int pageSize, int pageNumber) {
		IPage<RuleDetail> page = new Page<>(pageNumber, pageSize);
		QueryWrapper<RuleDetail> objectQueryWrapper = new QueryWrapper<>();
		if (sceneId != null) {

			objectQueryWrapper.eq(RuleDetail.COL_SCENES_ID, sceneId);
		}
		if (StringUtils.hasText(name)) {
			objectQueryWrapper.like(RuleDetail.COL_NAME, name);
		}
		if (StringUtils.hasText(sceneName)) {
			List<Integer> sceneIds = scenesService.findByName(sceneName);
			objectQueryWrapper.in(RuleDetail.COL_SCENES_ID, sceneIds);
		}
		IPage ruleDetailIPage = ruleDetailMapper.selectPage(page, objectQueryWrapper);

		List<RuleDetail> records = ruleDetailIPage.getRecords();
		List<RuleDetailVO> res = new ArrayList<>();

		for (RuleDetail record : records) {
			Integer scenesId = record.getScenesId();

			Scenes scenes = this.scenesService.findById(scenesId);
			String opName = OperatorEnums.oc(record.getOperator()).getCode();
			RuleDetailVO r = new RuleDetailVO(record.getId(), record.getScenesId(), record.getExpression(), record.getComparisonValue(),
					record.getOperator(), record.getName(), scenes.getName(), opName);
			res.add(r);
		}
		ruleDetailIPage.setRecords(res);
		return ruleDetailIPage;
	}


	public RuleDetail byId(Integer ruleDetailId) {
		return this.ruleDetailMapper.selectById(ruleDetailId);
	}

	public List<RuleDetail> ids(List<Integer> ids) {
		return this.ruleDetailMapper.selectBatchIds(ids);
	}

	public List<RuleDetailVO> exp(String exp) {
		List<RuleDetailVO> res = new ArrayList<>();
		List<RuleDetail> ids = ids(extractRuleId(exp));
		for (RuleDetail record : ids) {
			Integer scenesId = record.getScenesId();

			Scenes scenes = this.scenesService.findById(scenesId);
			String opName = OperatorEnums.oc(record.getOperator()).getCode();
			RuleDetailVO r = new RuleDetailVO(record.getId(), record.getScenesId(), record.getExpression(), record.getComparisonValue(),
					record.getOperator(), record.getName(), scenes.getName(), opName);
			res.add(r);
		}
		return res;
	}

	private List<Integer> extractRuleId(String expression) {

		List<String> ss = new ArrayList<String>();
		for (String sss : expression.replaceAll("[^0-9]", ",").split(",")) {
			if (sss.length() > 0) {
				ss.add(sss);
			}
		}
		List<Integer> collect = ss.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		return collect;
	}

	public List<RuleDetailVO> listForScId(Integer scId) {
		List<RuleDetailVO> res = new ArrayList<>();
		QueryWrapper<RuleDetail> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(RuleDetail.COL_SCENES_ID, scId);
		List<RuleDetail> ids = this.ruleDetailMapper.selectList(queryWrapper);
		for (RuleDetail record : ids) {
			Integer scenesId = record.getScenesId();

			Scenes scenes = this.scenesService.findById(scenesId);
			String opName = OperatorEnums.oc(record.getOperator()).getCode();
			RuleDetailVO r = new RuleDetailVO(record.getId(), record.getScenesId(), record.getExpression(), record.getComparisonValue(),
					record.getOperator(), record.getName(), scenes.getName(), opName);
			res.add(r);
		}
		return res;
	}
}
