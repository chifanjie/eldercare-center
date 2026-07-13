package edu.ysu.backend.controller;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Welcome;
import edu.ysu.backend.service.WelcomeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "敏感操作数据接口")
@RequestMapping("/welcome")
// 与用户登录，注册，密码操作，验证码相关的接口
public class WelcomeController {

    @Autowired
    private WelcomeService welcomeService;

    /***
     * 用户登录接口
     * @param welcome {username,password}
     * @return Result {code,msg,success,data{token}}
     */
    @PostMapping("/login")
    public Result login(@RequestBody Welcome welcome){
        return welcomeService.userLogin(welcome);
    }

    /***
     * 用户注册接口
     * @param welcome {username,password,email,rePassword,captcha,vailCode}
     * @return Result {code,msg,success}
     */
    @PostMapping("/register")
    public Result register(@RequestBody Welcome welcome){
        return welcomeService.userRegister(welcome);
    }

    /***
     * 忘记密码接口
     * @param welcome {username,password,email,rePassword,captcha,vailCode}
     * @return Result {code,msg,success}
     */
    @PutMapping("/forget")
    public Result forget(@RequestBody Welcome welcome){
        return welcomeService.forgetPassword(welcome);
    }

    /***
     * 账户是否存在接口
     * @param welcome {username,email}
     * @return Result {code,msg,success}
     */
    @PostMapping("/count_exist")
    public Result countExist(@RequestBody Welcome welcome){
        return welcomeService.isCountExist(welcome);
    }

    /***
     * 发送验证码接口
     * @param email 电子邮箱地址
     * @return Result {code,msg,success,data{captcha}}
     */
    @GetMapping("/captcha")
    public Result captcha(String email){
        return welcomeService.sendCaptcha(email);
    }

}
