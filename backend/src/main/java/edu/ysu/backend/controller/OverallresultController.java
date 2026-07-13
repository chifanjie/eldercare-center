package edu.ysu.backend.controller;


import edu.ysu.backend.entity.Overallresult;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.service.IOverallresultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2023-09-03
 */
@RestController
@CrossOrigin
@RequestMapping("/overallresult")
public class OverallresultController {
    @Autowired
    IOverallresultService iOverallresultService;

    @PutMapping("/add")
    public CommonResult add(@RequestBody Overallresult overallresult){
        return iOverallresultService.add(overallresult);
    }
    @GetMapping("/find/orderid/{orderid}")
    public CommonResult selectbyoid(@PathVariable int orderid){
        return iOverallresultService.selectByOid(orderid);
    }
    @PostMapping("/update")
    public CommonResult update(@RequestBody Overallresult overallresult){
        return iOverallresultService.update(overallresult);
    }

    @GetMapping("/totalnum/{orderid}")
    public CommonResult totalnum(@PathVariable int orderid){
        return iOverallresultService.totalnum(orderid);
    }

}
