package edu.ysu.backend.service.impl;

import edu.ysu.backend.entity.Cidetailedreport;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.mapper.CidetailedreportMapper;
import edu.ysu.backend.service.ICidetailedreportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since   
 */
@Service
public class CidetailedreportServiceImpl implements ICidetailedreportService {
    @Autowired
    CidetailedreportMapper cidetailedreportMapper;

    @Override
    public CommonResult add(Cidetailedreport cidetailedreport) {
        int i=cidetailedreportMapper.insert(cidetailedreport);
        int key=cidetailedreport.getCidrid();
        CommonResult commonResult=CommonResult.success(key);
        return commonResult;
    }

    @Override
    public CommonResult findbyoid(int orderid) {
        List<Cidetailedreport> list=new ArrayList<>();
        list=cidetailedreportMapper.findbyoid(orderid);
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }

    @Override
    public CommonResult update(Cidetailedreport cidetailedreport) {
        int i=cidetailedreportMapper.updateById(cidetailedreport);
        CommonResult commonResult=CommonResult.success(i);
        return commonResult;
    }
}
