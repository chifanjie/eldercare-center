package edu.ysu.backend.service;

import edu.ysu.backend.entity.Cidetailedreport;
import edu.ysu.backend.entity.common.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since   
 */
public interface ICidetailedreportService  {


    CommonResult add(Cidetailedreport cidetailedreport);

    CommonResult findbyoid(int orderid);

    CommonResult update(Cidetailedreport cidetailedreport);
}
