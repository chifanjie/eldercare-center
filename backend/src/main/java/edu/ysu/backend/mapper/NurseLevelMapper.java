package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.NurseLevel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NurseLevelMapper extends BaseMapper<NurseLevel> {
}
