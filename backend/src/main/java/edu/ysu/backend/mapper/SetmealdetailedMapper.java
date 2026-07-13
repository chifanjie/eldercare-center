package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Setmealdetailed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2023-08-30
 */
@Mapper
public interface SetmealdetailedMapper extends BaseMapper<Setmealdetailed> {

    @Select("select * from setmealdetailed where smid=#{smid}")
    @ResultMap("SetMealWithCheckItem")
    List<Setmealdetailed> selectbysmid(int smid);
}
