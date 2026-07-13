package edu.ysu.backend.controller;


import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.service.ISetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2023-08-27
 */
@RestController
@CrossOrigin
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    ISetmealService iSetmealService;

    @GetMapping("/g/all")
    public CommonResult findAll(){
        return iSetmealService.findAll();
    }

    @GetMapping("/g/select")
    public CommonResult findSelect(){
        return iSetmealService.findSelect();
    }

    @GetMapping("/findall")
    public CommonResult findALLwithdetail(){
        return iSetmealService.findALLwithdetail();
    }



}
