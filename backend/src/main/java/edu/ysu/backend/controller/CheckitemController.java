package edu.ysu.backend.controller;


import edu.ysu.backend.service.ICheckitemService;
import edu.ysu.backend.entity.common.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2023-08-31
 */
@RestController
@RequestMapping("/checkitem")
@CrossOrigin
public class CheckitemController {
    @Autowired
    ICheckitemService iCheckitemService;
    @GetMapping("/f/all")
    public CommonResult findALL(){
        return iCheckitemService.findAll();
    }
    @GetMapping("/find/byid/{ciid}")
    public  CommonResult findbyid(@PathVariable int ciid){

        return iCheckitemService.findbyid(ciid);
    }
}
