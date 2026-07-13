package edu.ysu.backend.controller;


import edu.ysu.backend.entity.Cireport;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.service.ICireportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2023-09-01
 */
@RestController
@RequestMapping("/cireport")
@CrossOrigin
public class CireportController {
    @Autowired
    ICireportService iCireportService;

    @PutMapping("/add")
    public CommonResult add(@RequestBody Cireport cireport){
        return iCireportService.add(cireport);
    }

}
