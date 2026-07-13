package edu.ysu.backend.controller;


import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.service.ISetmealdetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2023-08-30
 */
@RestController
@CrossOrigin
@RequestMapping("/setmealdetailed")
public class SetmealdetailedController {
    @Autowired
    ISetmealdetailedService iSetmealdetailedService;

    @GetMapping("/f/all")
   public CommonResult findall(){
       return iSetmealdetailedService.findall();
   }


   @GetMapping("/f/bysmid/{smid}")
   public CommonResult findbysmid(@PathVariable int smid){
        return iSetmealdetailedService.findbysmid(smid);
   }


}
