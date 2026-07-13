package edu.ysu.backend.service;

import edu.ysu.backend.entity.Hospital;
import edu.ysu.backend.entity.common.CommonResult;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2023-08-25
 */
public interface IHospitalService {

    CommonResult findAll();

    CommonResult findById(String hpid);

    CommonResult add(Hospital hospital);

    CommonResult findallBypage(int currentPage, int pageSize);

    CommonResult findallHospital();

    CommonResult update(Hospital hospital);
}
