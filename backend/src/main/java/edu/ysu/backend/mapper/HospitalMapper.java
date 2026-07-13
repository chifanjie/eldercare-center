package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Hospital;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2023-08-25
 */
@Mapper
public interface HospitalMapper extends BaseMapper<Hospital> {

    @Select("select * from hospital where hpid=#{id}")
    public Hospital find(int id);

    @Select("select * from hospital where state!=0 limit #{arg0},#{arg1}")
    public  List<Hospital> findallBypage(int start, int pageSize);

    @Select("select * from hospital where state !=0")
    public List<Hospital> findtotal();
}
