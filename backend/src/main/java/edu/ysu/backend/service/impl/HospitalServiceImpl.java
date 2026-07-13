package edu.ysu.backend.service.impl;

import edu.ysu.backend.entity.Hospital;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.mapper.HospitalMapper;
import edu.ysu.backend.service.IHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2023-08-25
 */
@Service
public class HospitalServiceImpl  implements IHospitalService {
    @Autowired
    HospitalMapper hospitalMapper;

    @Override
    public CommonResult findAll() {
        List<Hospital> list=new ArrayList<>();
        list=hospitalMapper.findtotal();
        int total=list.size();
        CommonResult commonResult=new CommonResult(total);
        return commonResult;
    }

    @Override
    public CommonResult findById(String hpid) {
        Hospital hospital=hospitalMapper.selectById(hpid);
        CommonResult commonResult=new CommonResult(hospital);
        return commonResult;
    }

    @Override
    public CommonResult add(Hospital hospital) {
        int i=hospitalMapper.insert(hospital);
        int key=hospital.getHpid();
        CommonResult commonResult=new CommonResult(key);
        return commonResult;
    }

    @Override
    public CommonResult findallBypage(int currentPage, int pageSize) {
        int start=(currentPage-1)*pageSize;
        List<Hospital> list=new ArrayList<>();
        list=hospitalMapper.findallBypage(start,pageSize);
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }

    @Override
    public CommonResult findallHospital() {
        List<Hospital> list=new ArrayList<>();
        list=hospitalMapper.findtotal();
        CommonResult commonResult=new CommonResult(list);
        return commonResult;

    }

    @Override
    public CommonResult update(Hospital hospital) {
        int i=hospitalMapper.updateById(hospital);
        CommonResult commonResult=CommonResult.success(i);
        return commonResult;
    }
}
