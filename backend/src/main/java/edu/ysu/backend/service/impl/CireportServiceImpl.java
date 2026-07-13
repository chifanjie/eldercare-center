package edu.ysu.backend.service.impl;

import edu.ysu.backend.entity.Cireport;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.mapper.CireportMapper;
import edu.ysu.backend.service.ICireportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2023-09-01
 */
@Service
public class CireportServiceImpl  implements ICireportService {
    @Autowired
    CireportMapper cireportMapper;

    @Override
    public CommonResult add(Cireport cireport) {
        int i=cireportMapper.insert(cireport);
        int key=cireport.getCirid();
        CommonResult commonResult=CommonResult.success(key);
        return commonResult;
    }
}
