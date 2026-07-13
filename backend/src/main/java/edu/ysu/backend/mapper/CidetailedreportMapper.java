package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Cidetailedreport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since   
 */
@Mapper
public interface CidetailedreportMapper extends BaseMapper<Cidetailedreport> {

        @Select("select * from cidetailedreport where orderid=#{orderid}")
        List<Cidetailedreport> findbyoid(int orderid);
}
