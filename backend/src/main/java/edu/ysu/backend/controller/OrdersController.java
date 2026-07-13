package edu.ysu.backend.controller;


import edu.ysu.backend.entity.Orders;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2023-08-27
 */

@RequestMapping("/orders")
@RestController
@CrossOrigin
public class OrdersController {
    @Autowired
    IOrdersService iOrdersService;

    @GetMapping("/f/owithy/{currentPage}/{pageSize}")
    public CommonResult getAllOrdersWithUser(@PathVariable int currentPage , @PathVariable int pageSize ){
        return iOrdersService.getAllOrdersWithUser(currentPage,pageSize);
    }

    @GetMapping("/f/all")
    public CommonResult findall(){
        return iOrdersService.findAll();
    }

    @PostMapping ("/f/sql/{currentPage}/{pageSize}")
    public CommonResult findBySql(@PathVariable int currentPage , @PathVariable int pageSize,
                                  String userid,  String realname, String sex,
                                   String smid,String orderDate, String state){
        return iOrdersService.findBySql(currentPage,pageSize,userid,realname,sex,smid,orderDate,state);
    }

    @PutMapping("/add")
    public CommonResult add(@RequestBody Orders orders){
        return iOrdersService.add(orders);
    }

    @GetMapping("/count")
    public CommonResult count(){
        return iOrdersService.count();
    }

    @GetMapping("/findOrder/{userid}")
    public CommonResult findbyUser(@PathVariable int userid){
        return iOrdersService.findbyUser(userid);
    }
    @PostMapping("/update")
    public CommonResult update(@RequestBody Orders orders){
        return iOrdersService.update(orders);
    }
    @PostMapping("/updatestate")
    public CommonResult updatestate(@RequestBody Orders orders){
        return iOrdersService.updatestate(orders);
    }

    @GetMapping("/findOrders/{userid}")
    public CommonResult findguidang(@PathVariable int userid){
        return iOrdersService.findguidang(userid);
    }
    @GetMapping("/weiguidang/{userid}")
    public CommonResult findweiguidang(@PathVariable int userid){
        return iOrdersService.findweiguidang(userid);
    }
    @GetMapping("/quxiao/{userid}")
    public CommonResult findquxiao(@PathVariable int userid){
        return iOrdersService.findquxiao(userid);
    }

    @GetMapping("/getnum")
    public CommonResult ordernum(){
        return iOrdersService.ordernum();
    }
    @GetMapping("/getorderover")
    public CommonResult orderyover(){
        return iOrdersService.orderyover();
    }
    @GetMapping("/getnum1")
    public CommonResult setmealnum1(){
        return iOrdersService.setmealnum1();
    }
    @GetMapping("/getnum2")
    public CommonResult setmealnum2(){
        return iOrdersService.setmealnum2();
    }
    @GetMapping("/getnum3")
    public CommonResult setmealnum3(){
        return iOrdersService.setmealnum3();
    }




}
