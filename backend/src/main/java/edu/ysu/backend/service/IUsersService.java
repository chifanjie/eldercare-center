package edu.ysu.backend.service;

import edu.ysu.backend.entity.Users;
import edu.ysu.backend.entity.common.CommonResult;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2023-08-26
 */
public interface IUsersService  {

    CommonResult add(Users users);

    CommonResult findall();

    CommonResult findbyid(String useride);

    CommonResult update(Users users);



    CommonResult login(String userid, String password) throws UnsupportedEncodingException;

    CommonResult findallpage(int currentPage, int pageSize);

    CommonResult findtotal();

    CommonResult register(Users users);

    CommonResult findman();

    CommonResult findwomen();

    CommonResult findbyidpage(int currentPage, int pageSize,String userid);
}
