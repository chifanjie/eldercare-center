package edu.ysu.backend.service;

import edu.ysu.backend.entity.Doctor;
import edu.ysu.backend.entity.common.CommonResult;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2023-09-05
 */
public interface IDoctorService {

    CommonResult add(Doctor doctor);

    CommonResult findall(int currentPage, int pageSize);

    CommonResult findtotal();

    CommonResult update(Doctor doctor);

    CommonResult login(String doccode, String password) throws UnsupportedEncodingException;
}
