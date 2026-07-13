package edu.ysu.backend.service;

import edu.ysu.backend.entity.Overallresult;
import edu.ysu.backend.entity.common.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2023-09-03
 */
public interface IOverallresultService {


    CommonResult add(Overallresult overallresult);

    CommonResult selectByOid(int orderid);

    CommonResult update(Overallresult overallresult);

    CommonResult totalnum(int orderid);
}
