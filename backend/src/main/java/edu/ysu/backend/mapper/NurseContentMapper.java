package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.NurseContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NurseContentMapper extends BaseMapper<NurseContent> {

    @Select("SELECT name FROM nurse_content WHERE cid = #{cid}")
    String selectContentName(Integer cid);

}
