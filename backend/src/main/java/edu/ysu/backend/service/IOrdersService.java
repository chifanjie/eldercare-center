package edu.ysu.backend.service;

import edu.ysu.backend.entity.Orders;
import edu.ysu.backend.entity.common.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2023-08-27
 */
public interface IOrdersService  {

    CommonResult getAllOrdersWithUser(int currentPage,int pageSize);

    CommonResult findAll();

    CommonResult findBySql(int currentPage,int pageSize,String userid, String realname, String sex, String smid, String orderDate, String  state);


    CommonResult add(Orders orders);

    CommonResult count();


    CommonResult findbyUser(int userid);

    CommonResult update(Orders orders);

    CommonResult updatestate(Orders orders);

    CommonResult findguidang(int userid);

    CommonResult findweiguidang(int userid);

    CommonResult ordernum();

    CommonResult orderyover();

    CommonResult setmealnum1();

    CommonResult setmealnum2();

    CommonResult setmealnum3();

    CommonResult findquxiao(int userid);
}
