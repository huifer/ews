package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huifer.ews.domain.db.Process;
import com.github.huifer.ews.domain.res.ProcessVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProcessMapper extends BaseMapper<Process> {
	int updateBatch(List<Process> list);

	int updateBatchSelective(List<Process> list);

	int batchInsert(@Param("list") List<Process> list);

	int insertOrUpdate(Process record);

	int insertOrUpdateSelective(Process record);

	List<ProcessVO > queryPage(@Param("scenesId") Integer scenesId, @Param("name") String name, @Param("description") String description);
}