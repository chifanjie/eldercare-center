package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT role.del FROM user,role WHERE user.role=role.rid AND user.email=#{email}")
    int getRoleIsDel(String email);

}
