package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Usertype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2023-09-06
 */
@Mapper
public interface UsertypeMapper extends BaseMapper<Usertype> {
    @Select("select * from usertype where id=#{id}")
    public Usertype SelectByType(int id);
}
