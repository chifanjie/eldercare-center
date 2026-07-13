package edu.ysu.backend.service.impl;

import edu.ysu.backend.entity.Checkitem;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.mapper.CheckitemMapper;
import edu.ysu.backend.service.ICheckitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2023-08-31
 */
@Service
public class CheckitemServiceImpl implements ICheckitemService {

    @Autowired
    CheckitemMapper checkitemMapper;
    @Override
    public CommonResult findAll() {

        // Console payload output removed for archival safety.
        List<Checkitem> list=checkitemMapper.findal();
        // Console payload output removed for archival safety.
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }

    @Override
    public CommonResult findbyid(int ciid) {
        Checkitem checkitem=checkitemMapper.selectById(ciid);
        CommonResult commonResult=CommonResult.success(checkitem);
        return commonResult;
    }
}
