package edu.ysu.backend.service;

import edu.ysu.backend.entity.common.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2023-08-30
 */
public interface ISetmealdetailedService  {

    CommonResult findall();

    CommonResult findbysmid(int smid);
}
