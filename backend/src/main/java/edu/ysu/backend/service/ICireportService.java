package edu.ysu.backend.service;

import edu.ysu.backend.entity.Cireport;
import edu.ysu.backend.entity.common.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2023-09-01
 */
public interface ICireportService {

    CommonResult add(Cireport cireport);
}
