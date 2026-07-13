package edu.ysu.backend.controller;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Role;
import edu.ysu.backend.entity.vo.User;
import edu.ysu.backend.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "角色,用户数据接口")
@RequestMapping("/user")
// 与用户，角色相关的接口
public class UserController {

    @Autowired
    private UserService userService;

    /***
     * 获得当前登录用户详细信息接口
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/login_info")
    public Result loginUserinfo(){
        return userService.getLoginUserinfo();
    }

    /***
     * 添加角色接口
     * @param role 角色对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_role")
    public Result addRole(@RequestBody Role role){
        return userService.addRole(role);
    }

    /***
     * 禁用角色接口（逻辑删除）
     * @param role 角色对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/delete_role")
    public Result deleteRole(@RequestBody Role role){
        return userService.deleteRole(role.getRid());
    }

    /***
     * 修改角色详情时的数据回显
     * @param rid 角色id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/role_form_data")
    public Result getRoleSetFormData(Integer rid){
        return userService.getRoleFormData(rid);
    }

    /***
     * 角色数据记录分页查询
     * @param keyword 关键词
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/role_paged")
    public Result role(String keyword,int pageNum,int pageSize){
        return userService.getRolePaged(keyword, pageNum, pageSize);
    }

    /***
     * 修改角色详情接口
     * @param role 角色对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/role_change")
    public Result changeRole(@RequestBody Role role){
        return userService.changeRole(role);
    }

    /***
     * 用户退出（注销）接口
     * 注意：该接口没有被使用
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/logout")
    public Result logout(){
        return userService.logout();
    }

    /***
     * 用户数据记录分页查询
     * @param keyword 关键词
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/userinfo_paged")
    public Result userinfoPaged(String keyword,int pageNum,int pageSize){
        return userService.getUserinfoPaged(keyword, pageNum, pageSize);
    }

    /***
     * 上传用户头像接口
     * @param avatar 用户头像文件
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/upload_avatar")
    public Result uploadAvatar(@RequestBody MultipartFile[] avatar){
        return userService.uploadResult(avatar[0]);
    }

    /***
     * 添加用户信息接口
     * @param user 用户对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_userinfo")
    public Result addUser(@RequestBody User user){
        return userService.addUserinfo(user);
    }

    /***
     * 禁用用户接口
     * @param user 用户对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/delete_userinfo")
    public Result disableUser(@RequestBody User user){
        return userService.deleteUserinfo(user);
    }

    /***
     * 修改用户信息时数据回显接口
     * @param id 用户id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/userinfo_form_data")
    public Result getUserinfoFormData(Integer id){
        return userService.getUserinfoFormData(id);
    }

    /***
     * 修改用户信息接口
     * @param user 用户对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/userinfo_change")
    public Result changeUserinfo(@RequestBody User user){
        return userService.changeUserinfo(user);
    }

    /***
     * 用户个人资料数据接口
     * @param sid 用户id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/user_record_info")
    public Result getUserRecordInfo(Integer sid){
        return userService.getUserRecordInfo(sid);
    }

    /***
     * 修改用户个人资料数据接口
     * @param user 用户对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_user_record_info")
    public Result changeUserRecordInfo(@RequestBody User user){
        return userService.changeUserRecordInfo(user);
    }

    /***
     * 个人资料用户头像上传数据接口
     * @param avatar 用户头像文件
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/reset_user_avatar")
    public Result setUserAvatar(@RequestBody MultipartFile[] avatar){
        return userService.setUserAvatar(avatar[0]);
    }

}
