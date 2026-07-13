package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.Customer;
import edu.ysu.backend.entity.dto.Emergency;
import edu.ysu.backend.entity.dto.Family;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

    @Select("SELECT * FROM family_set WHERE uid=#{uid} LIMIT 3")
    List<Family> selectFamilyByUidLimited(Integer uid);

    @Select("SELECT * FROM emergency_set WHERE uid=#{uid} LIMIT 3")
    List<Emergency> selectEmergencyByUidLimited(Integer uid);

}
