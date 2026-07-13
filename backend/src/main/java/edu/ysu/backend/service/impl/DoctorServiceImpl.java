package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.ysu.backend.entity.Doctor;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.mapper.DoctorMapper;
import edu.ysu.backend.service.IDoctorService;
import edu.ysu.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2023-09-05
 */
@Service
public class DoctorServiceImpl  implements IDoctorService {
    @Autowired
    DoctorMapper doctorMapper;

    @Override
    public CommonResult add(Doctor doctor) {
        int i=doctorMapper.insert(doctor);
        int key=doctor.getDocid();
        CommonResult commonResult=CommonResult.success(key);
        return commonResult;
    }

    @Override
    public CommonResult findall(int currentPage, int pageSize) {
        List<Doctor> list=new ArrayList<>();
        int start=(currentPage-1)*pageSize;
        list=doctorMapper.findall(start,pageSize);
        CommonResult commonResult=CommonResult.success(list);
        return commonResult;
    }

    @Override
    public CommonResult findtotal() {
        List<Doctor> list=new ArrayList<>();
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("state",0);
        list=doctorMapper.selectList(queryWrapper);
        int total=list.size();
        CommonResult commonResult=CommonResult.success(total);
        return commonResult;
    }

    @Override
    public CommonResult update(Doctor doctor) {
        int i=doctorMapper.updateById(doctor);
        CommonResult commonResult=CommonResult.success(i);
        return commonResult;
    }

    @Override
    public CommonResult login(String doccode, String password) throws UnsupportedEncodingException {
          // Console payload output removed for archival safety.
//        Doctor doctor = doctorMapper.login(doccode, password);
//
//        if (doctor != null && doccode.equals(doctor.getDoccode()) && password.equals(doctor.getPassword())) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("doccode", doccode);
//            map.put("password", password);
//            String token = JwtUtil.buildToken(map);
//            //为实体类注入token
//            doctor.setToken(token);
//            CommonResult commonResult = null;
//            commonResult = CommonResult.success(doctor);
//            return commonResult;
//        }
//        else if (doctor != null && !doctor.equals(doctor.getDoccode())) {
//            CommonResult commonResult = CommonResult.failed("账户不存在");
//            return commonResult;
//        } else if (doctor != null && doctor.equals(doctor.getDoccode()) && !password.equals(doctor.getPassword())) {
//            CommonResult commonResult = CommonResult.failed("登录密码错误");
//            return commonResult;
//        }
        return null;
    }
}
