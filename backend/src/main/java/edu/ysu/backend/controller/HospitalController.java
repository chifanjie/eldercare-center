package edu.ysu.backend.controller;


import edu.ysu.backend.entity.Hospital;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.service.IHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2023-08-25
 */
@RestController
@CrossOrigin
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    IHospitalService iHospitalService;
    @GetMapping("/f/alltotal")
    public CommonResult findAll1(){
        return iHospitalService.findAll();
    }
    @GetMapping("f/byId/{id}")
    public CommonResult findById(@PathVariable("id") String hpid){
        return iHospitalService.findById(hpid);
    }

    @PutMapping("/add")
    public CommonResult add(@RequestBody Hospital hospital){
        return iHospitalService.add(hospital);
    }
    @GetMapping("/f/add/{currentPage}/{pageSize}")
    public CommonResult findallBypage(@PathVariable int currentPage , @PathVariable int pageSize){
        return iHospitalService.findallBypage(currentPage,pageSize);
    }

    @GetMapping("/f/all")
    public CommonResult findAll(){
        return iHospitalService.findallHospital();
    }

    @PostMapping("/update")
    public CommonResult update(@RequestBody Hospital hospital){
        return iHospitalService.update(hospital);
    }

}
