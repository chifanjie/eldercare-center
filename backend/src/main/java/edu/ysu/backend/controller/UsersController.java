package edu.ysu.backend.controller;


import edu.ysu.backend.entity.Users;
import edu.ysu.backend.entity.common.CommonResult;
import edu.ysu.backend.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2023-08-26
 */
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UsersController {
    @Autowired
    IUsersService iUsersService;

    @PutMapping("/add")
    public CommonResult add(@RequestBody Users users){
        return iUsersService.add(users);
    }
    @GetMapping("/f/all")
    public CommonResult findall(){
        return iUsersService.findall();
    }
    @GetMapping("/f/byid/{userid}")
    public CommonResult findbyid(@PathVariable("userid") String userid){
        return iUsersService.findbyid(userid);
    }
    @PostMapping("/update")
    public CommonResult update(@RequestBody Users users){
        return iUsersService.update(users);
    }

    @PostMapping("/login")
    public CommonResult login(String userid,String password) throws UnsupportedEncodingException {
        return iUsersService.login(userid,password);
    }
    @GetMapping("/findallpage/{currentPage}/{pageSize}")
    public  CommonResult findallpage(@PathVariable int currentPage,@PathVariable int pageSize){
        return iUsersService.findallpage(currentPage,pageSize);
    }
    @GetMapping("/findbyidpage/{currentPage}/{pageSize}/{userid}")
    public CommonResult findbyidpage(@PathVariable int currentPage,@PathVariable int pageSize,@PathVariable String userid){
        return iUsersService.findbyidpage(currentPage,pageSize,userid);
    }


    @GetMapping("/findtotal")
    public CommonResult findtotal(){
        return iUsersService.findtotal();
    }
    @PutMapping("/register")
    public CommonResult register(Users users){
        return iUsersService.register(users);
    }
    @GetMapping("/getman")
    public CommonResult findman(){
        return iUsersService.findman();
    }
    @GetMapping("/getwomen")
    public CommonResult findwomen(){
        return iUsersService.findwomen();
    }



}
