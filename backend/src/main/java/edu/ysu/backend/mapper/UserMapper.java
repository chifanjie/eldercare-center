package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.Staff;
import edu.ysu.backend.entity.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT staff.sid,staff.name,staff.gander,staff.avatar,staff.age,role.role AS role_name " +
            "FROM user,staff,role " +
            "WHERE user.uid=staff.sid " +
            "AND user.role=role.rid AND user.email=#{email}")
    Staff getUserInfo(String email);

}
