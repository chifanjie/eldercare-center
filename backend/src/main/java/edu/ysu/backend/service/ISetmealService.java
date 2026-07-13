package edu.ysu.backend.service;

import edu.ysu.backend.entity.common.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2023-08-27
 */
public interface ISetmealService {

    CommonResult findAll();

    CommonResult findSelect();

    CommonResult findALLwithdetail();
}
