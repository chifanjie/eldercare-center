package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Checkitem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2023-08-31
 */
@Mapper
public interface CheckitemMapper extends BaseMapper<Checkitem> {
    @Select("select * from checkitem where ciid=#{ciid}")
    @ResultMap("CheckitemwithDeatil")
    public List<Checkitem> findByCiid(int ciid);

    @Select("select * from checkitem")
    @ResultMap("CheckitemwithDeatil")
    public List<Checkitem> findal();




}
