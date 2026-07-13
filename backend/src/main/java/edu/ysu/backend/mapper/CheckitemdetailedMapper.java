package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Checkitemdetailed;
import org.apache.ibatis.annotations.Mapper;
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
public interface CheckitemdetailedMapper extends BaseMapper<Checkitemdetailed> {


    @Select("select * from checkitemdetailed where ciid=#{ciid}")
   public List<Checkitemdetailed> findbyciid(int ciid);

}
