package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2023-09-05
 */
@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {

    @Select("select * from doctor where state!=0 limit #{arg0},#{arg1}")
    public List<Doctor> findall(int start, int pageSize);
    @Select("select  *\n" +
            "from doctor\n" +
            "where  doccode =#{ arg0  } and `password` = #{ arg1 }  and state!=0")
   public Doctor login(String doccode, String password);

}
