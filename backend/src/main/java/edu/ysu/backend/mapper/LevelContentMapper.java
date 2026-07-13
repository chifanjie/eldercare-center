package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.LevelContentContrast;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LevelContentMapper extends BaseMapper<LevelContentContrast> {

    @Select("SELECT * FROM nurse_level WHERE del=0")
    List<LevelContentContrast> selectNurseLevel();


}
