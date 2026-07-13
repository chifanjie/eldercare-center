package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Sex;
import edu.ysu.backend.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SexMapper extends BaseMapper<Users> {

    @Select("select * from sex where id=#{id}")
    public Sex SelectBySex(int id);

}
