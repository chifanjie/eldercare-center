package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.Menue;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenueMapper extends BaseMapper<Menue> {

    @Select("SELECT menue.mid,menue.name,menue.path,menue.parent,menue.memo,menue.del,menue.icon " +
            "FROM role,user,role_menue_contrast,menue " +
            "WHERE role.rid=user.role AND role.rid=role_menue_contrast.rid AND menue.mid=role_menue_contrast.mid " +
            "AND user.email=#{email}")
    List<Menue> selectMenueByRole(String email);

    @Insert("INSERT INTO role_menue_contrast(rid,mid) VALUES(#{rid},#{mid})")
    int insetIntoContrast(@Param("rid") Integer rid,@Param("mid") Integer mid);

    @Select("SELECT menue.name " +
            "FROM role,role_menue_contrast,menue " +
            "WHERE role.rid=role_menue_contrast.rid AND menue.mid=role_menue_contrast.mid " +
            "AND role.rid=#{rid} AND menue.del=0 AND menue.parent=0 AND menue.mid AND menue.mid NOT IN (4,5) LIMIT 3")
    List<String> selectRoleAuth(Integer rid);

    @Select("SELECT menue.mid  " +
            "FROM role,menue,role_menue_contrast " +
            "WHERE role.rid=role_menue_contrast.rid AND menue.mid=role_menue_contrast.mid " +
            "AND role.rid=#{rid} AND menue.del=0 AND menue.parent!=0 AND menue.mid NOT IN (4,5)")
    List<String> selectPathByRid(Integer rid);

    @Delete("DELETE FROM role_menue_contrast WHERE rid=#{rid}")
    int deleteContrastByRid(Integer rid);

    @Select("SELECT mid FROM role_menue_contrast WHERE rid=#{rid}")
    List<Integer> selectContrastByRid(Integer rid);


}
