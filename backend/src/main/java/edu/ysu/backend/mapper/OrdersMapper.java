package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2023-08-27
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    @ResultMap("OrderWithUser")
    @Select("select * from orders where state!=0 limit #{arg0},#{arg1}")
    List<Orders> findOrderWithUser(int param1,int param2);

    List<Orders> findBySql(@Param("start") int start,@Param("pageSize") int pageSize,@Param("userid") String userid, @Param("realname") String realname, @Param("sex") String sex, @Param("smid") String smid,
                           @Param("orderDate") String orderDate,@Param("state") String state);


    List<Orders> findBySql2(@Param("userid") String userid, @Param("realname") String realname, @Param("sex") String sex, @Param("smid") String smid,
                           @Param("orderDate") String orderDate,@Param("state") String state);

    @Select("select * from orders where userid=#{userid} and state!=0")
    @ResultMap("OrderWithUser")
    List<Orders> findByUser(int userid);
    @Select("select * from orders where state!=0")
    List<Orders> findtotal();

    @Select("select * from orders where userid=#{userid} and state=2")
    @ResultMap("OrderWithUser")
    List<Orders> findguidang(int userid);
    @Select("select * from orders where userid=#{userid} and state=1")
    @ResultMap("OrderWithUser")
    List<Orders> findweiguidang(int userid);

    @Select("select * from orders where userid=#{userid} and state=0")
    @ResultMap("OrderWithUser")
    List<Orders> findquxiao(int userid);

}
