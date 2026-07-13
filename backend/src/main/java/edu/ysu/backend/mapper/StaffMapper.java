package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StaffMapper extends BaseMapper<Staff> {

    @Select("SELECT uid FROM user WHERE role=6")
    List<Integer> selectButlerId();

    @Select("SELECT uid FROM user WHERE role = 4")
    List<Integer> selectSupportId();

}
