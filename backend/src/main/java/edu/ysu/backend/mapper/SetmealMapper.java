package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2023-08-27
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    @Select("Select * from setmeal where smid=#{id}")
    @ResultMap("SetmealMapperwithdetailed")
    public Setmeal find(int id);

    @Select("select smid,name from setmeal")
    public List<Setmeal> findSelect();

    @Select("select * from setmeal")
    @ResultMap("SetmealMapperwithdetailed")
    public List<Setmeal> findall();

}
