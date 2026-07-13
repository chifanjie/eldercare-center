package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Users;
import edu.ysu.backend.entity.dto.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2023-08-26
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {


    @Select("select * from users where userid=#{userid}")
    @ResultMap("UserMap")
    public Users find(int userid);

    @Select("select  *\n" +
            "from users\n" +
            "where  userid =#{ arg0  } and `password` = #{ arg1 }  and usertype!=0")
    public Users login(String userid,String password);

    @Select("select * from users where usertype!=0 limit #{arg0},#{arg1}")
    @ResultMap("UserMap")
    public List<Users> findallpage(int start, int pageSize);

    @Select("select * from users where userid like '%${userid}%' limit #{arg0},#{arg1}")
    public List<Users> findbyidpage(String userid,int start,int pagesize);

    @Select("select * from users where userid=#{userid}")
    public Users findbyuserid(String userid);

}
