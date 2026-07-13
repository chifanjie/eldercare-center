package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.ysu.backend.entity.Users;
import edu.ysu.backend.entity.dto.Staff;
import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Welcome;
import edu.ysu.backend.entity.dto.User;
import edu.ysu.backend.mapper.StaffMapper;
import edu.ysu.backend.mapper.UserMapper;
import edu.ysu.backend.mapper.UsersMapper;
import edu.ysu.backend.service.WelcomeService;
import edu.ysu.backend.util.EmailUtil;
import edu.ysu.backend.util.JwtUtil;
import edu.ysu.backend.util.RandomCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WelcomeServiceImpl implements WelcomeService
{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private UsersMapper usersMapper;
    @Override
    public Result sendCaptcha(String email) {
        String randCode= RandomCaptcha.randomCode();
        String title="【邮箱验证码服务】欢迎使用颐养中心后台管理系统服务";
        String content= "<h5>您正在使用邮箱"+email+"进行验证操作，本次验证码为：<span style='color: red;font-size: 20px'>"+randCode+"</span></h5>"+
                "<h5>请不要暴露您的验证码给任何人，如非您本人操作，请忽略本邮件。不需要回复本邮件</h5>"+
                "<h5 style='text-align: right'>————SSymbol</h5>";
        Result result = emailUtil.captchaEmail(email, title, content);
        if (result.isSuccess()){
            return Result.ok("验证码已发送!").data("captcha",randCode);
        }else {
            return Result.fail("验证码发送失败!");
        }
    }

    @Override
    public Result userRegister(Welcome welcome) {
        if (welcome.getVailCode().equals(welcome.getCaptcha())){
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("email",welcome.getEmail());
            User usr = userMapper.selectOne(queryWrapper);
            if (usr!=null){
                log.info("Operation event recorded");
                return Result.fail("该邮箱已被注册!");
            }else {
                QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
                userQueryWrapper.eq("username",welcome.getUsername());
                usr = userMapper.selectOne(userQueryWrapper);
                if (usr!=null){
                    log.info("该用户名已注册");
                    return Result.fail("该用户名已注册");
                }else {
                    User user=User.builder()
                            .username(welcome.getUsername())
                            .password(welcome.getPassword())
                            .email(welcome.getEmail())
                            .role(2)
                            .del(0).build();
                            int insert = userMapper.insert(user);
                    Staff staff=Staff.builder()
                            .avatar("default.png")
                            .name(welcome.getUsername())
                            .gander("男")
                            .age("0")
                            .sid(user.getUid()).build();
                            int insertStaff = staffMapper.insert(staff);
                    Users users=Users.builder()
                            .realname(welcome.getUsername())
                            .sex(1)
                            .userid(String.valueOf(user.getUid())).build();
                            int inserts = usersMapper.insert(users);



                    return insert>0&&insertStaff>0&&inserts>0?Result.ok("注册成功!"):Result.fail("注册失败!");
                }
            }
        }else {
            log.info("验证码错误");
            log.info("Operation event recorded");
            log.info("Operation event recorded");
            return Result.fail("验证码错误!");
        }
    }

    @Override
    public Result userLogin(Welcome welcome) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",welcome.getUsername()).and(condition->condition.eq("password",welcome.getPassword()));
        User user = userMapper.selectOne(queryWrapper);
        if (user!=null){
            if(user.getDel()==1){
                return Result.fail("该账户已被禁用!");
            }else {
                String token = JwtUtil.createJWT(user.getEmail());
                log.info("Operation event recorded");
                log.info("Operation event recorded");
                return Result.ok(user.getUsername()+"欢迎回来!").data("token",token);
            }
        }else {
            return Result.fail("用户名或密码错误!");
        }
    }

    @Override
    public Result isCountExist(Welcome welcome) {
        if (welcome.getVailCode().equals(welcome.getCaptcha())){
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("username",welcome.getUsername()).and(condition->condition.eq("email",welcome.getEmail()));
            User user = userMapper.selectOne(queryWrapper);
            if (user!=null){
                log.info("用户存在");
                return Result.ok("用户存在");
            }else {
                log.info("用户不存在");
                return Result.fail("用户不存在!");
            }
        }else {
            log.info("验证码错误");
            log.info("Operation event recorded");
            log.info("Operation event recorded");
            return Result.fail("验证码错误!");
        }
    }

    @Override
    public Result forgetPassword(Welcome welcome) {
        User user=User.builder().password(welcome.getPassword()).build();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("email",welcome.getEmail());
        int update = userMapper.update(user, queryWrapper);
        log.info("Operation event recorded");
        log.info("Operation event recorded");
        return update>0?Result.ok("修改成功"):Result.fail("修改失败");
    }

}
