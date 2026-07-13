package edu.ysu.backend.service;

import edu.ysu.backend.entity.common.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2023-08-31
 */
public interface ICheckitemService {


    CommonResult findAll();

    CommonResult findbyid(int ciid);
}
