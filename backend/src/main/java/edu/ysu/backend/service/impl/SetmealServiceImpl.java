package edu.ysu.backend.service.impl;

import edu.ysu.backend.entity.Setmeal;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.mapper.SetmealMapper;
import edu.ysu.backend.service.ISetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2023-08-27
 */
@Service
public class SetmealServiceImpl  implements ISetmealService {

    @Autowired
    SetmealMapper setmealMapper;
    @Override
    public CommonResult findAll() {
        List<Setmeal> list=new ArrayList<>();
        list=setmealMapper.selectList(null);
        CommonResult commonResult=new CommonResult(list);
        return commonResult;
    }

    @Override
    public CommonResult findSelect() {
        List<Setmeal> list=new ArrayList<>();
        list=setmealMapper.findSelect();
        CommonResult commonResult=new CommonResult(list);
        return commonResult;
    }

    @Override
    public CommonResult findALLwithdetail() {
        List<Setmeal> list=new ArrayList<>();
        list=setmealMapper.findall();
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }
}
