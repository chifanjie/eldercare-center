package edu.ysu.backend.service.impl;

import edu.ysu.backend.entity.Overallresult;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.mapper.OverallresultMapper;
import edu.ysu.backend.service.IOverallresultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2023-09-03
 */
@Service
public class OverallresultServiceImpl implements IOverallresultService {

    @Autowired
    OverallresultMapper overallresultMapper;

    @Override
    public CommonResult add(Overallresult overallresult) {
        int i=overallresultMapper.insert(overallresult);
        int key=overallresult.getOrid();
        CommonResult commonResult=CommonResult.success(key);
        return commonResult;
    }

    @Override
    public CommonResult selectByOid(int orderid) {
        List<Overallresult> list=overallresultMapper.SelectByOid(orderid);
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }

    @Override
    public CommonResult update(Overallresult overallresult) {
        int i=overallresultMapper.updateById(overallresult);
        CommonResult commonResult=CommonResult.success(i);
        return commonResult;
    }

    @Override
    public CommonResult totalnum(int orderid) {
        List<Overallresult> list=overallresultMapper.SelectByOid(orderid);
        int num=list.size();
        CommonResult commonResult=CommonResult.success(num);
        return commonResult;
    }
}
