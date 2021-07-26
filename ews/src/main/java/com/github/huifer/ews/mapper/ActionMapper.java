package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.huifer.ews.domain.db.Action;

import java.util.List;

import com.github.huifer.ews.domain.res.ActionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ActionMapper extends BaseMapper<Action> {
	int updateBatch(List<Action> list);

	int updateBatchSelective(List<Action> list);

	int batchInsert(@Param("list") List<Action> list);

	int insertOrUpdate(Action record);

	int insertOrUpdateSelective(Action record);

	IPage<ActionVO> query(@Param("scenesName") String scenesName, @Param("httpMethod") String httpMethod, @Param("url") String url, @Param("objectPage") Page<ActionVO> objectPage);
}