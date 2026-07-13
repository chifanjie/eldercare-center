package edu.ysu.backend.service.impl;

import edu.ysu.backend.entity.Setmealdetailed;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.mapper.SetmealdetailedMapper;
import edu.ysu.backend.service.ISetmealdetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2023-08-30
 */
@Service
public class SetmealdetailedServiceImpl  implements ISetmealdetailedService {
    @Autowired
    SetmealdetailedMapper setmealdetailedMapper;

    @Override
    public CommonResult findall() {
        List<Setmealdetailed> list=setmealdetailedMapper.selectList(null);
        CommonResult commonResult= CommonResult.success(list);

        return commonResult;
    }

    @Override
    public CommonResult findbysmid(int smid) {
        List<Setmealdetailed> list=setmealdetailedMapper.selectbysmid(smid);
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }


}
