package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huifer.ews.domain.db.ProcessTrue;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProcessTrueMapper extends BaseMapper<ProcessTrue> {
    int updateBatch(List<ProcessTrue> list);

    int updateBatchSelective(List<ProcessTrue> list);

    int batchInsert(@Param("list") List<ProcessTrue> list);

    int insertOrUpdate(ProcessTrue record);

    int insertOrUpdateSelective(ProcessTrue record);
}