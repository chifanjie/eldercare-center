package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.ysu.backend.entity.Orders;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.mapper.OrdersMapper;
import edu.ysu.backend.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2023-08-27
 */
@Service
public class OrdersServiceImpl  implements IOrdersService {
    @Autowired
    OrdersMapper ordersMapper;


    public CommonResult getAllOrdersWithUser(int currentPage,int pageSize){
        List<Orders> list=new ArrayList<>();
        int start=(currentPage-1)*pageSize;
        list=ordersMapper.findOrderWithUser(start,pageSize);
        CommonResult commonResult=new CommonResult(list);
        return commonResult;
    }

    @Override
    public CommonResult findAll() {
        List<Orders> list=new ArrayList<>();
        list=ordersMapper.selectList(null);
        CommonResult commonResult=new CommonResult(list);
        return commonResult;
    }

    @Override
    public CommonResult findBySql(int currentPage,int pageSize,String userid, String realname, String sex, String smid, String  orderDate, String state) {
        List<Orders> list=new ArrayList<>();
        int start =(currentPage-1)*pageSize;
        list=ordersMapper.findBySql(start,pageSize,userid, realname,sex,smid, orderDate, state);
        int i=ordersMapper.findBySql2(userid, realname,sex,smid, orderDate, state).size();
        CommonResult commonResult=CommonResult.success(list,i);
        return commonResult;
    }

    @Override
    public CommonResult add(Orders orders) {
        int i=ordersMapper.insert(orders);
        int key=orders.getOrderid();
        CommonResult commonResult=CommonResult.success(key);

        return commonResult;
    }

    @Override
    public CommonResult count() {
        List<Orders> list=ordersMapper.findtotal();
        int count=list.size();
        CommonResult commonResult=CommonResult.success(count);
        return commonResult;
    }

    @Override
    public CommonResult findbyUser(int userid) {
        List<Orders> list =new ArrayList<>();
        list=ordersMapper.findByUser(userid);
        CommonResult commonResult=CommonResult.success(list);

        return commonResult;
    }

    @Override
    public CommonResult update(Orders orders) {
        int i=ordersMapper.updateById(orders);
        CommonResult commonResult=CommonResult.success(i);
        return commonResult;
    }

    @Override
    public CommonResult updatestate(Orders orders) {
        int i=ordersMapper.updateById(orders);
        CommonResult commonResult=CommonResult.success(i);
        return commonResult;
    }

    @Override
    public CommonResult findguidang(int userid) {
        List<Orders> list=ordersMapper.findguidang(userid);
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }

    @Override
    public CommonResult findweiguidang(int userid) {
        List<Orders> list=ordersMapper.findweiguidang(userid);
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }

    @Override
    public CommonResult ordernum() {
        List<Orders> list=ordersMapper.findtotal();
        int num=list.size();
        CommonResult commonResult=CommonResult.success(num);
        return commonResult;
    }

    @Override
    public CommonResult orderyover() {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",2);
        List<Orders> list=ordersMapper.selectList(queryWrapper);
        CommonResult commonResult=CommonResult.success(list.size());
        return commonResult;
    }

    @Override
    public CommonResult setmealnum1() {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("smid",1);
        queryWrapper.ne("state",0);
        List<Orders> list=ordersMapper.selectList(queryWrapper);
        CommonResult commonResult=CommonResult.success(list.size());
        return commonResult;
    }

    @Override
    public CommonResult setmealnum2() {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("smid",2);
        queryWrapper.ne("state",0);
        List<Orders> list=ordersMapper.selectList(queryWrapper);
        CommonResult commonResult=CommonResult.success(list.size());
        return commonResult;
    }

    @Override
    public CommonResult setmealnum3() {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("smid",3);
        queryWrapper.ne("state",0);
        List<Orders> list=ordersMapper.selectList(queryWrapper);
        CommonResult commonResult=CommonResult.success(list.size());
        return commonResult;
    }

    @Override
    public CommonResult findquxiao(int userid) {
        List<Orders> list=ordersMapper.findquxiao(userid);
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }


}
