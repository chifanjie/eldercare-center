package edu.ysu.backend.controller;


import edu.ysu.backend.entity.Doctor;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2023-09-05
 */
@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    IDoctorService iDoctorService;

    @PutMapping("/add")
    public CommonResult add(@RequestBody Doctor doctor){
        return iDoctorService.add(doctor);
    }
    @GetMapping("/findall/{currentPage}/{pageSize}")
    public CommonResult findall(@PathVariable int currentPage,@PathVariable int pageSize){
        return iDoctorService.findall(currentPage,pageSize);
    }
    @GetMapping("/findtotal")
    public CommonResult findtotal(){
        return iDoctorService.findtotal();
    }

    @PostMapping("/update")
    public CommonResult update(@RequestBody Doctor doctor){
        return iDoctorService.update(doctor);
    }

    @PostMapping("/login")
    public CommonResult login(String  doccode, String password) throws UnsupportedEncodingException {
            return iDoctorService.login(doccode,password);
    }


}
