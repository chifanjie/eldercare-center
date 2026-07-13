package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.ysu.backend.entity.Users;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.mapper.UsersMapper;
import edu.ysu.backend.service.IUsersService;
import edu.ysu.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2023-08-26
 */
@Service
public class UsersServiceImpl  implements IUsersService {
    @Autowired
    UsersMapper usersMapper;

    @Override
    public CommonResult add(Users users) {
        int i = usersMapper.insert(users);
        String key = users.getUserid();
        CommonResult commonResult = new CommonResult(key);
        return commonResult;
    }

    @Override
    public CommonResult findall() {
        List<Users> list = new ArrayList<>();
        list = usersMapper.selectList(null);
        CommonResult commonResult = new CommonResult(list);
        return commonResult;
    }

    @Override
    public CommonResult findbyid(String userid) {
        Users users = usersMapper.selectById(userid);
        CommonResult commonResult = new CommonResult(users);
        return commonResult;
    }

    @Override
    public CommonResult update(Users users) {
        int i = usersMapper.updateById(users);
        CommonResult commonResult = new CommonResult(i);
        return commonResult;
    }


    @Override
    public CommonResult login(String userid, String password) throws UnsupportedEncodingException {
//        Users users = usersMapper.login(userid, password);
//        if (users != null && userid.equals(users.getUserid()) && password.equals(users.getPassword())) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("userid", userid);
//            map.put("password", password);
//            String token = JWTUtil.buildToken(map);
//            //为实体类注入token
//            users.setToken(token);
//            CommonResult commonResult = null;
//            commonResult = CommonResult.success(users);
//            return commonResult;
//        }
//        else if (users != null && !userid.equals(users.getUserid())) {
//            CommonResult commonResult = CommonResult.failed("账户不存在");
//            return commonResult;
//        } else if (users != null && userid.equals(users.getUserid()) && !password.equals(users.getPassword())) {
//            CommonResult commonResult = CommonResult.failed("登录密码错误");
//            return commonResult;
//        }
        return null;
    }

    @Override
    public CommonResult findallpage(int currentPage, int pageSize) {
        int start=(currentPage-1)*pageSize;
        List<Users> list=usersMapper.findallpage(start,pageSize);
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }

    @Override
    public CommonResult findtotal() {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("usertype",0);
        List<Users> list=usersMapper.selectList(queryWrapper);
        int total=list.size();
        CommonResult commonResult=CommonResult.success(total);
        return commonResult;
    }

    @Override
    public CommonResult register(Users users) {
        String userid=users.getUserid();
        Users users1=usersMapper.findbyuserid(userid);
        if(users1!=null){
            CommonResult commonResult=CommonResult.failed(users1);
            return commonResult;
        }
        else {
           int i=usersMapper.insert(users);
           String key=users.getUserid();
           CommonResult commonResult=CommonResult.success(key);
           return commonResult;
        }

    }

    @Override
    public CommonResult findman() {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("usertype",0);
        queryWrapper.eq("sex",1);
        List<Users> list=usersMapper.selectList(queryWrapper);
        int total=list.size();
        CommonResult commonResult=CommonResult.success(total);
        return commonResult;
    }

    @Override
    public CommonResult findwomen() {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("usertype",0);
        queryWrapper.eq("sex",2);
        List<Users> list=usersMapper.selectList(queryWrapper);
        int total=list.size();
        CommonResult commonResult=CommonResult.success(total);
        return commonResult;
    }

    @Override
    public CommonResult findbyidpage(int currentPage, int pageSize,String userid) {
        IPage<Users> empPage = new Page<Users>(currentPage, pageSize);
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        if(userid!="") {
            queryWrapper.like("userid", userid);
//        queryWrapper.orderByAsc("sal");
            IPage<Users> rest = usersMapper.selectPage(empPage, queryWrapper);
            CommonResult commonResult = CommonResult.success(rest);
            return commonResult;
        }
        else{

//        queryWrapper.orderByAsc("sal");
            IPage<Users> rest = usersMapper.selectPage(empPage, null);
            CommonResult commonResult = CommonResult.success(rest);
            return commonResult;
        }


    }
}
