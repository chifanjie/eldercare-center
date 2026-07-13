package edu.ysu.backend.controller;


import edu.ysu.backend.entity.Cidetailedreport;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.service.ICidetailedreportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since
 */
@RestController
@CrossOrigin
@RequestMapping("/cidetailedreport")
public class CidetailedreportController {
    @Autowired
    ICidetailedreportService iCidetailedreportService;

    @PutMapping("/add")
    public CommonResult add(@RequestBody Cidetailedreport cidetailedreport){
        return iCidetailedreportService.add(cidetailedreport);
    }
    @GetMapping("/byoid/{orderid}")
    public CommonResult findbyoid(@PathVariable int orderid){
        return iCidetailedreportService.findbyoid(orderid);
    }

    @PostMapping("/update")
    public CommonResult update(@RequestBody Cidetailedreport cidetailedreport){
        return iCidetailedreportService.update(cidetailedreport);
    }

}
