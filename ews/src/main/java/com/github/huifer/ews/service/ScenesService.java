package com.github.huifer.ews.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.huifer.ews.domain.db.Scenes;
import com.github.huifer.ews.domain.dto.ProcessFull;
import com.github.huifer.ews.domain.dto.ScenesFull;
import com.github.huifer.ews.mapper.ScenesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenesService {
	@Autowired
	private ScenesMapper scenesMapper;
	@Autowired
	private ProcessService processService;

	public Scenes saveOrUpdate(Scenes scenes) {
		int i = scenesMapper.insertOrUpdateSelective(scenes);
		return scenes;
	}

	public IPage<Scenes> query(String name, String description, int pageSize, int pageNumber) {
		IPage<Scenes> page = new Page<>(pageNumber, pageSize);
		QueryWrapper<Scenes> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(Scenes.COL_NAME, name);
		queryWrapper.like(Scenes.COL_DESCRIPTION, description);
		IPage<Scenes> page1 = scenesMapper.selectPage(page, queryWrapper);
		return page1;
	}

	public ScenesFull full(Integer sceneId) {
		Scenes scenes = this.scenesMapper.selectById(sceneId);
		if (scenes != null) {

			ScenesFull scenesFull = new ScenesFull();
			scenesFull.setScenes(scenes);
			List<ProcessFull> fulls = processService.fulls(sceneId);
			scenesFull.setProcessFulls(fulls);
			return scenesFull;
		}
		return null;
	}


}
