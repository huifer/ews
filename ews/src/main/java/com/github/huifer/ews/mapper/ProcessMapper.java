package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huifer.ews.domain.db.Process;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProcessMapper extends BaseMapper<Process> {
    int updateBatch(List<Process> list);

    int updateBatchSelective(List<Process> list);

    int batchInsert(@Param("list") List<Process> list);

    int insertOrUpdate(Process record);

    int insertOrUpdateSelective(Process record);
}