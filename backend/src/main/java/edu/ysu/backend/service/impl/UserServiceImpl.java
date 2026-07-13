package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.ysu.backend.entity.dto.Menue;
import edu.ysu.backend.entity.dto.Role;
import edu.ysu.backend.entity.pojo.OnlineContext;
import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.mapper.MenueMapper;
import edu.ysu.backend.mapper.RoleMapper;
import edu.ysu.backend.mapper.StaffMapper;
import edu.ysu.backend.mapper.UserMapper;
import edu.ysu.backend.service.UserService;
import edu.ysu.backend.util.FileUtil;
import edu.ysu.backend.util.JwtUtil;
import edu.ysu.backend.entity.dto.Staff;
import edu.ysu.backend.entity.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MenueMapper menueMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Value("${resources.avatar}")
    private String avatarPath;

    @Override
    public Result getLoginUserinfo() {
        String current = OnlineContext.getCurrent();
        String email = JwtUtil.parseJWT(current).getSubject();
        Staff staff=userMapper.getUserInfo(email);
        staff.setAvatar(avatarPath+staff.getAvatar());
        staff.setRole(userMapper.selectOne(new QueryWrapper<User>().eq("uid",staff.getSid())).getRole());
        return Result.ok("查询当前登录用户成功").data("online",staff);
    }

    @Override
    public Result addRole(edu.ysu.backend.entity.vo.Role role) {
        Role addRole= Role.builder()
                .role(role.getRole())
                .del(role.getDel())
                .memo(role.getMemo()).build();
        int insert = roleMapper.insert(addRole);
        if (insert>0){
            Set<String> parentSet=new HashSet<>();
            for (String mid:role.getAuth()){
                QueryWrapper<Menue> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("mid",Integer.parseInt(mid));
                Menue menue = menueMapper.selectOne(queryWrapper);
                parentSet.add(String.valueOf(menue.getParent()));
            }
            List<String> menueId=new ArrayList<>();
            menueId.addAll(role.getAuth());
            menueId.addAll(parentSet);
            menueId.add("4");
            menueId.add("5");
            for (String mid:menueId){
                int i = menueMapper.insetIntoContrast(addRole.getRid(), Integer.valueOf(mid));
            }
        }
        log.info("Operation event recorded");
        return insert>0?Result.ok("创建角色成功"):Result.fail("创建角色失败");
    }

    @Override
    public Result getRolePaged(String keyword,int pageNum,int pageSize) {
        Page<Role> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        if ("".equals(keyword)||keyword==null){
            queryWrapper.notIn("rid",2,1);
        }else {
            queryWrapper.like("role",keyword).and(condition->condition.notIn("rid",2,1));
        }
        IPage<Role> roleIPage = roleMapper.selectPage(page, queryWrapper);
        List<Role> records = roleIPage.getRecords();
        for (Role role :records){
            role.setAuth(menueMapper.selectRoleAuth(role.getRid()));
        }
        return Result.ok("查询角色信息成功").data("recordP",roleIPage);
    }

    @Override
    public Result getRoleFormData(Integer rid) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("rid",rid);
        Role rol = roleMapper.selectOne(queryWrapper);
        edu.ysu.backend.entity.vo.Role role= edu.ysu.backend.entity.vo.Role.builder()
                .role(rol.getRole())
                .memo(rol.getMemo())
                .del(rol.getDel())
                .auth(menueMapper.selectPathByRid(rid)).build();
        return Result.ok("查询成功").data("form",role);
    }

    @Override
    public Result deleteRole(Integer rid) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("rid",rid);
        Role role= Role.builder().del(1).build();
        int update = roleMapper.update(role, queryWrapper);
        return update>0?Result.ok("禁用角色成功"):Result.fail("禁用角色失败");
    }

    @Override
    public Result changeRole(edu.ysu.backend.entity.vo.Role role) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("rid",role.getRid());
        Role updateRole= Role.builder()
                .rid(role.getRid())
                .memo(role.getMemo())
                .role(role.getRole())
                .del(role.getDel()).build();
        int roleUpdate = roleMapper.updateById(updateRole);
        if (roleUpdate>0){
            if (!menueMapper.selectContrastByRid(role.getRid()).isEmpty()){
                menueMapper.deleteContrastByRid(role.getRid());
            }
            Set<String> parentSet=new HashSet<>();
            for (String mid:role.getAuth()){
                QueryWrapper<Menue> queryWrapperParent=new QueryWrapper<>();
                queryWrapperParent.eq("mid",Integer.parseInt(mid));
                Menue menue = menueMapper.selectOne(queryWrapperParent);
                parentSet.add(String.valueOf(menue.getParent()));
            }
            role.getAuth().addAll(parentSet);
            role.getAuth().add("4");
            role.getAuth().add("5");
            for (String mid:role.getAuth()){
                int i = menueMapper.insetIntoContrast(role.getRid(), Integer.valueOf(mid));
            }
            return Result.ok("修改角色信息成功");
        }else {
            return Result.fail("修改角色信息失败");
        }
    }

    @Override
    public Result logout() {
        OnlineContext.removeCurrent();
        return Result.ok(OnlineContext.getCurrent()+"退出成功");
    }

    @Override
    public Result getUserinfoPaged(String keyword, int pageNum, int pageSize) {
        Page<Staff> staffPage=new Page<>(pageNum,pageSize);
        QueryWrapper<Staff> queryWrapperStaff=new QueryWrapper<>();
        if ("".equals(keyword)||keyword==null){
            queryWrapperStaff.notIn("sid",3,4);
        }else {
            queryWrapperStaff.like("name",keyword).and(condition->condition.notIn("sid",3,4));
        }
        List<User> users=userMapper.selectList(null);
        IPage<Staff> staffIPage = staffMapper.selectPage(staffPage, queryWrapperStaff);
        List<Staff> records = staffIPage.getRecords();
        for (Staff staff:records){
            for (User user:users){
                if (Objects.equals(staff.getSid(), user.getUid())){
                    staff.setUsername(user.getUsername());
                    staff.setPassword(user.getPassword());
                    staff.setEmail(user.getEmail());
                    staff.setRole(user.getRole());
                }
            }
            staff.setAvatar(avatarPath+staff.getAvatar());
        }
        return Result.ok("查询用户信息成功").data("recordP",staffIPage);
    }

    @Override
    public Result uploadResult(MultipartFile avatar) {
        Result res = FileUtil.saveFile(avatar, "avatar");
        if (res.getCode()==200){
            return Result.ok("上传头像成功").data("avatar",res.getData());
        }else {
            return Result.fail("上传头像失败");
        }
    }

    @Override
    public Result addUserinfo(edu.ysu.backend.entity.vo.User user) {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("email",user.getEmail());
        User exist = userMapper.selectOne(userQueryWrapper);
        if (exist!=null){
            return Result.fail("该邮箱已被注册");
        }else {
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("username",user.getUsername());
            exist = userMapper.selectOne(queryWrapper);
            if (exist!=null){
                return Result.fail("该用户名已被注册");
            }else {
                User usr=User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .role(Integer.valueOf(user.getRole()))
                        .del(Integer.valueOf(user.getDel()))
                        .email(user.getEmail()).build();
                Staff staff=Staff.builder()
                        .name(user.getName())
                        .age(user.getAge())
                        .gander(user.getGander())
                        .del(usr.getDel())
                        .tel(user.getTel())
                        .memo(user.getMemo()).build();
                if ("".equals(user.getAvatar())||user.getAvatar()==null){
                    staff.setAvatar("default.png");
                }else {
                    staff.setAvatar(user.getAvatar());
                }
                int insertUser = userMapper.insert(usr);
                staff.setSid(usr.getUid());
                int insertStaff = staffMapper.insert(staff);
                return insertUser>0&&insertStaff>0?Result.ok("添加账户成功"):Result.fail("添加账户失败");
            }
        }
    }

    @Override
    public Result deleteUserinfo(edu.ysu.backend.entity.vo.User user) {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        QueryWrapper<Staff> staffQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("uid",user.getSid());
        staffQueryWrapper.eq("sid",user.getSid());
        User usr=User.builder().del(1).build();
        Staff staff=Staff.builder().del(1).build();
        int updateUser = userMapper.update(usr, userQueryWrapper);
        int updateStaff = staffMapper.update(staff, staffQueryWrapper);
        return updateStaff>0&&updateUser>0?Result.ok("禁用账户成功"):Result.fail("禁用账户失败");
    }

    @Override
    public Result getUserinfoFormData(Integer id) {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        QueryWrapper<Staff> staffQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("uid",id);
        staffQueryWrapper.eq("sid",id);
        User user=userMapper.selectOne(userQueryWrapper);
        Staff staff=staffMapper.selectOne(staffQueryWrapper);
        edu.ysu.backend.entity.vo.User userInfo= edu.ysu.backend.entity.vo.User.builder()
                .sid(user.getUid())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .del(String.valueOf(user.getDel()))
                .name(staff.getName())
                .gander(staff.getGander())
                .age(staff.getAge())
                .tel(staff.getTel())
                .memo(staff.getMemo())
                .avatar(avatarPath+staff.getAvatar()).build();
        return Result.ok("查询成功").data("form",userInfo);
    }

    @Override
    public Result changeUserinfo(edu.ysu.backend.entity.vo.User user) {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        User usr=User.builder()
                .uid(user.getSid())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .del(Integer.valueOf(user.getDel()))
                .role(Integer.valueOf(user.getRole())).build();
        Staff staff=Staff.builder()
                .sid(user.getSid())
                .name(user.getName())
                .gander(user.getGander())
                .age(user.getAge())
                .memo(user.getMemo())
                .tel(user.getTel())
                .del(usr.getDel()).build();
        if ("".equals(user.getAvatar())||user.getAvatar()==null){
            staff.setAvatar("default.png");
        }
        userQueryWrapper.eq("uid",user.getSid());
        User isUserUpdate=userMapper.selectOne(userQueryWrapper);
        if (isUserUpdate.getUsername().equals(user.getUsername())&&isUserUpdate.getEmail().equals(user.getEmail())){
            staff.setAvatar(user.getAvatar());
            int userUpdate = userMapper.updateById(usr);
            int staffUpdate = staffMapper.updateById(staff);
            return userUpdate>0&&staffUpdate>0?Result.ok("修改账户成功"):Result.fail("修改账户失败");
        }else {
            userQueryWrapper.eq("username",user.getUsername());
            User exist=userMapper.selectOne(userQueryWrapper);
            if (exist!=null){
                return Result.fail("该用户名已存在");
            }else {
                userQueryWrapper.eq("email",user.getEmail());
                exist=userMapper.selectOne(userQueryWrapper);
                if (exist!=null){
                    return Result.fail("该邮箱已被注册");
                }else {
                    staff.setAvatar(user.getAvatar());
                    int userUpdate = userMapper.updateById(usr);
                    int staffUpdate = staffMapper.updateById(staff);
                    return userUpdate>0&&staffUpdate>0?Result.ok("修改账户成功"):Result.fail("修改账户失败");
                }
            }
        }
    }

    @Override
    public Result getUserRecordInfo(Integer sid) {
        QueryWrapper<Staff> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("sid",sid);
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("uid",sid);
        User user=userMapper.selectOne(userQueryWrapper);
        Staff staff = staffMapper.selectOne(queryWrapper);
        staff.setAvatar(avatarPath+staff.getAvatar());
        staff.setEmail(user.getEmail());
        staff.setPassword(user.getPassword());
        return Result.ok("查询个人资料成功").data("staff",staff);
    }

    @Override
    public Result changeUserRecordInfo(edu.ysu.backend.entity.vo.User user) {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("uid",user.getSid());
        User usr=userMapper.selectOne(userQueryWrapper);
        Staff staffUpdate=Staff.builder()
                .sid(user.getSid())
                .name(user.getName())
                .age(user.getAge())
                .gander(user.getGander())
                .tel(user.getTel()).build();
        User userUpdate=User.builder()
                .uid(user.getSid())
                .email(user.getEmail())
                .password(user.getPassword()).build();
        if (usr.getEmail().equals(user.getEmail())){
            int update = staffMapper.updateById(staffUpdate);
            int updated = userMapper.updateById(userUpdate);
            return updated>0&&update>0?Result.ok("修改成功"):Result.fail("修改失败");
        }else {
            QueryWrapper<User> emailQueryWrapper=new QueryWrapper<>();
            emailQueryWrapper.eq("email",user.getEmail());
            User emailUser=userMapper.selectOne(emailQueryWrapper);
            if (emailUser!=null){
                return Result.fail("该邮箱已被注册");
            }else {
                int update = staffMapper.updateById(staffUpdate);
                int updated = userMapper.updateById(userUpdate);
                return updated>0&&update>0?Result.ok("修改成功"):Result.fail("修改失败");
            }
        }
    }

    @Override
    public Result setUserAvatar(MultipartFile avatar) {
        String current = OnlineContext.getCurrent();
        String email = JwtUtil.parseJWT(current).getSubject();
        Staff staff=userMapper.getUserInfo(email);
        Result res = FileUtil.saveFile(avatar, "avatar");
        if (res.getCode()==200){
            Staff update=Staff.builder()
                    .sid(staff.getSid())
                    .avatar((String) res.getData().get("name")).build();
            int updated = staffMapper.updateById(update);
            return updated>0?Result.ok("上传头像成功"):Result.fail("上传头像失败");
        }else {
            return Result.fail("上传头像失败");
        }
    }

    @Override
    public Result deleteUserTrash() {
        int deleteUser=0;
        int deleteStaff=0;
        List<User> userList=userMapper.selectList(new QueryWrapper<User>().eq("del",1));
        List<Staff> staffList=staffMapper.selectList(new QueryWrapper<Staff>().eq("del",1));
        if (userList.isEmpty()&&staffList.isEmpty()){
            return Result.ok("无需清理");
        }else {
            for (User user:userList){
                deleteUser=deleteUser+ userMapper.delete(new QueryWrapper<User>().eq("uid",user.getUid()));
            }
            for (Staff staff:staffList){
                deleteStaff=deleteStaff+ staffMapper.delete(new QueryWrapper<Staff>().eq("sid",staff.getSid()));
            }
            return deleteStaff>0&&deleteUser>0?Result.ok("清理成功"):Result.fail("清理失败");
        }
    }
}
