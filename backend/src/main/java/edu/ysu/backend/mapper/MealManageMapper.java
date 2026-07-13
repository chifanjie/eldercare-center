package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.MealManage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MealManageMapper extends BaseMapper<MealManage> {

    @Select("SELECT mid FROM meal_customer_contrast WHERE cid=#{cid}")
    List<Integer> selectMealCustomer(Integer cid);

    @Delete("DELETE FROM meal_customer_contrast WHERE cid=#{cid}")
    Integer deleteMealCustomerContrastAll(Integer cid);

    @Insert("INSERT INTO meal_customer_contrast(cid,mid) VALUES(#{cid},#{mid})")
    Integer insertMealCustomerContrast(@Param("cid") Integer cid,@Param("mid") Integer mid);

}
