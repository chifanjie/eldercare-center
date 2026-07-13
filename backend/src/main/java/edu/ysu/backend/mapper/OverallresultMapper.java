package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Overallresult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2023-09-03
 */
@Mapper
public interface OverallresultMapper extends BaseMapper<Overallresult> {
    @Select("select * from overallresult where orderid=#{orderid} and state=1")
    public List<Overallresult> SelectByOid(int orderid);

}
