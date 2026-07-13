package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.Bed;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BedMapper extends BaseMapper<Bed> {
}
