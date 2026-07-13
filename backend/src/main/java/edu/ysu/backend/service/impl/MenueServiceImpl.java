package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.ysu.backend.entity.dto.*;
import edu.ysu.backend.entity.pojo.OnlineContext;
import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Customer;
import edu.ysu.backend.entity.vo.Menu;;
import edu.ysu.backend.mapper.*;
import edu.ysu.backend.service.MenueService;
import edu.ysu.backend.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MenueServiceImpl implements MenueService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private MenueMapper menueMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private MealMapper mealMapper;
    @Autowired
    private NurseContentMapper nurseContentMapper;
    @Autowired
    private LevelContentMapper levelContentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private NurseLevelMapper nurseLevelMapper;

    @Override
    public Result getMenue() {
        String current = OnlineContext.getCurrent();
        String email = JwtUtil.parseJWT(current).getSubject();
        List<Menue> menueByRole;
        List<Menu> menueList=new ArrayList<>();
        int isRoleIsExist=roleMapper.getRoleIsDel(email);
        if (isRoleIsExist==0){
            menueByRole = menueMapper.selectMenueByRole(email);
        }else {
            menueByRole = menueMapper.selectMenueByRole("SYSTEM_ADMIN_ROLE");
        }
        for (Menue menue:menueByRole){
            if (menue.getParent()==0){
                Menu father=Menu.builder()
                        .mid(menue.getMid())
                        .name(menue.getName())
                        .path(menue.getPath())
                        .icon(menue.getIcon())
                        .children(new ArrayList<>()).build();
                menueList.add(father);
            }else {
                for (Menu menu:menueList){
                    if (menue.getParent().equals(menu.getMid())){
                        Menu child=Menu.builder()
                                .mid(menue.getMid())
                                .name(menue.getName())
                                .path(menue.getPath())
                                .icon(menue.getIcon()).build();
                        menu.getChildren().add(child);
                    }
                }
            }
        }
        Collections.reverse(menueList);
        return Result.ok("获取权限菜单").data("menue",menueList);
    }

    @Override
    public Result roleMenue() {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.notIn("rid",1);
        List<Role> roles=roleMapper.selectList(queryWrapper);
        return Result.ok("查询角色菜单成功").data("roleMenue",roles);
    }

    @Override
    public Result getTreeMenue() {
        QueryWrapper<Menue> queryWrapper=new QueryWrapper<>();
        queryWrapper.notIn("mid",new ArrayList<>(Arrays.asList(4,5)));
        List<Menue> menues=menueMapper.selectList(queryWrapper);
        List<Menu> menueList=new ArrayList<>();
        for (Menue menue:menues){
            if (menue.getParent()==0){
                Menu father=Menu.builder()
                        .mid(menue.getMid())
                        .value(String.valueOf(menue.getMid()))
                        .label(menue.getName())
                        .children(new ArrayList<>()).build();
                menueList.add(father);
            }else {
                for (Menu menu:menueList){
                    if (menue.getParent().equals(menu.getMid())){
                        Menu child=Menu.builder()
                                .value(String.valueOf(menue.getMid()))
                                .label(menue.getName()).build();
                        menu.getChildren().add(child);
                    }
                }
            }
        }
        Collections.reverse(menueList);
        return Result.ok("获取树形菜单").data("tree",menueList);
    }

    @Override
    public Result getNurseMenue() {
        QueryWrapper<NurseContent> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("del",0);
        List<NurseContent> nurseContents = nurseContentMapper.selectList(queryWrapper);
        return Result.ok("获取护理内容菜单").data("nurseMenue",nurseContents);
    }

    @Override
    public Result getBuildingMenue() {
        List<Building> origin=buildingMapper.selectBuildingMenue();
        List<Map<String, Object>> menu = new ArrayList<>();
        Map<String, Object> roomsByBuilding = new HashMap<>();
        Set<Integer> bid=new HashSet<>();
        // 遍历原始数据列表
        for (Building entry : origin) {
            String buildingName = entry.getName();
            String roomName = entry.getRoomName();
            String bedName = entry.getBedName();
            int eid = entry.getEid();
            int rid = entry.getRid();
            bid.add(entry.getBid());
            // 检查 buildings 映射是否已包含当前 building
            if (!roomsByBuilding.containsKey(buildingName)) {
                roomsByBuilding.put(buildingName, new ArrayList<>());
            }
            List<Map<String, Object>> rooms = (List<Map<String, Object>>) roomsByBuilding.get(buildingName);
            boolean roomExists = false;
            for (Map<String, Object> room : rooms) {
                if (room.get("label").equals(roomName)) {
                    roomExists = true;
                    // 检查当前房间是否包含该床位
                    List<Map<String, Object>> beds = (List<Map<String, Object>>) room.get("children");
                    boolean bedExists = false;
                    for (Map<String, Object> bed : beds) {
                        if (bed.get("label").equals(bedName)) {
                            bedExists = true;
                            break; // 如果床位已存在，则不添加
                        }
                    }
                    if (!bedExists) {
                        beds.add(new HashMap<String, Object>() {{
                            put("label", Integer.valueOf(bedName));
                            put("value", String.valueOf(rid)+"-"+String.valueOf(eid));
                        }});
                    }
                    break;
                }
            }
            if (!roomExists) {
                // 如果房间不存在，则添加房间和床位
                List<Map<String, Object>> newBeds = new ArrayList<>();
                newBeds.add(new HashMap<String, Object>() {{
                    put("label", Integer.valueOf(bedName));
                    put("value", String.valueOf(rid)+"-"+String.valueOf(eid));
                }});
                rooms.add(new HashMap<String, Object>() {{
                    put("label", roomName);
                    put("value",rid);
                    put("children", newBeds);
                }});
            }
        }
        // 构建最终的 menu 结构
        for (Map.Entry<String, Object> entry : roomsByBuilding.entrySet()) {
            String buildingName = entry.getKey();
            List<Map<String, Object>> rooms = (List<Map<String, Object>>) entry.getValue();
            menu.add(new HashMap<String, Object>() {{
                put("label", buildingName);
                QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("name",buildingName);
                put("value",buildingMapper.selectOne(queryWrapper).getBid());
                put("children", rooms);
            }});
        }
        log.info("Operation event recorded");
        return Result.ok("查询建筑房间菜单成功").data("buildingMenue",menu);
    }

    @Override
    public Result getCustomerMenue(String keyword) {
        QueryWrapper<edu.ysu.backend.entity.dto.Customer> queryWrapper = new QueryWrapper<>();
        if (keyword==null||"".equals(keyword)){
            queryWrapper.eq("del",0);
        }else {
            queryWrapper.like("name",keyword)
                    .and(condition->condition.eq("del",0));
        }
        List<edu.ysu.backend.entity.dto.Customer> customers = customerMapper.selectList(queryWrapper.last("LIMIT 6"));
        List<Customer> menue=new ArrayList<>();
        for (edu.ysu.backend.entity.dto.Customer customer : customers) {
            menue.add(Customer.builder().label(customer.getName()).value(customer.getUid()).build());
        }
        return Result.ok("查询客户菜单成功").data("customerMenue",menue);
    }

    @Override
    public Result getStaffMenue(String keyword) {
        QueryWrapper<Staff> queryWrapper=new QueryWrapper<>();
        if (keyword==null||"".equals(keyword)){
            queryWrapper.eq("del",0)
                    .and(condition->condition.notIn("sid",new ArrayList<>(Arrays.asList(3,4))));
        }else {
            queryWrapper.like("name",keyword)
                    .and(condition->condition.eq("del",0))
                    .and(condition->condition.notIn("sid",new ArrayList<>(Arrays.asList(3,4))))
                    .last("LIMIT 6");
        }
        List<Staff> staffs = staffMapper.selectList(queryWrapper);
        List<edu.ysu.backend.entity.vo.Staff> menue=new ArrayList<>();
        for (Staff staff : staffs){
            menue.add(edu.ysu.backend.entity.vo.Staff.builder().label(staff.getName()).value(staff.getSid()).build());
        }
        return Result.ok("查询员工菜单成功").data("staffMenue",menue);
    }

    @Override
    public Result getTagMenue() {
        return Result.ok("查询标签菜单成功").data("tagMenue",mealMapper.selectTagList());
    }

    @Override
    public Result getTypeMenue() {
        return Result.ok("查询类别菜单成功").data("typeMenue",mealMapper.selectTypeList());
    }

    @Override
    public Result getMealMenue() {
        return Result.ok("查询膳食菜单成功").data("mealMenue",mealMapper.selectList(null));
    }

    @Override
    public Result getMealsTagMenue() {
        List<Meal> meals = mealMapper.selectList(null);
        for (Meal meal : meals){
            meal.setTagName(mealMapper.selectTagName(meal.getTid()));
        }
        return Result.ok("查询膳食菜单成功").data("mealMenue",meals);
    }

    @Override
    public Result getLevelMenue() {
        List<LevelContentContrast> levelContents = levelContentMapper.selectList(null);
        Set<Integer> nidList=new HashSet<>();
        for (LevelContentContrast levelContentContrast: levelContents){
            nidList.add(levelContentContrast.getNid());
        }
        return Result.ok("查询服务级别菜单成功").data("levelMenue",nurseLevelMapper.selectList(new QueryWrapper<NurseLevel>().in("nid",nidList)));
    }

    @Override
    public Result getButlerMenue(String keyword) {
        List<Integer> uidList=staffMapper.selectButlerId();
        QueryWrapper<Staff> queryWrapper=new QueryWrapper<>();
        if ("".equals(keyword)||keyword==null){
            queryWrapper.in("sid",uidList)
                    .and(condition->condition.eq("del",0));
        }else {
            queryWrapper.in("sid",uidList)
                    .and(condition->condition.like("name",keyword))
                    .and(condition->condition.eq("del",0));
        }
        List<Staff> staffs = staffMapper.selectList(queryWrapper.last("LIMIT 6"));
        return Result.ok("查询管家菜单成功").data("staffs",staffs);
    }

    @Override
    public Result getSupportMenue(String keyword) {
        List<Integer> uidList=staffMapper.selectSupportId();
        QueryWrapper<Staff> queryWrapper=new QueryWrapper<>();
        if ("".equals(keyword)||keyword==null){
            queryWrapper.in("sid",uidList)
                    .and(condition->condition.eq("del",0));
        }else {
            queryWrapper.in("sid",uidList)
                    .and(condition->condition.like("name",keyword))
                    .and(condition->condition.eq("del",0));
        }
        List<Staff> staffs = staffMapper.selectList(queryWrapper.last("LIMIT 6"));
        return Result.ok("查询护工菜单成功").data("staffs",staffs);
    }

    @Override
    public Result getServiceMenue(String keyword) {
        QueryWrapper<NurseContent> queryWrapper=new QueryWrapper<>();
        if (keyword==null||"".equals(keyword)){
            queryWrapper.eq("del",0);
        }else {
            queryWrapper.like("name",keyword).and(condition->condition.eq("del",0));
        }
        List<NurseContent> menue = nurseContentMapper.selectList(queryWrapper.last("LIMIT 6"));
        return Result.ok("查询护理内容菜单成功").data("serviceMenue",menue);
    }

    @Override
    public Result customCustomerServiceMenue(String keyword) {
        String current = OnlineContext.getCurrent();
        String email = JwtUtil.parseJWT(current).getSubject();
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("email",email));
        QueryWrapper<edu.ysu.backend.entity.dto.Customer> queryWrapper=new QueryWrapper<>();
        if (user.getRole()==1){
            if (keyword==null||"".equals(keyword)){
                queryWrapper.eq("del",0);
            }else {
                queryWrapper.like("name",keyword).and(condition->condition.eq("del",0));
            }
        }else {
            List<Integer> uidList=serviceMapper.selectButlerProvider(user.getUid());
            if (uidList.isEmpty()){
                queryWrapper.eq("uid",-1);
            }else {
                if (keyword==null||"".equals(keyword)){
                    queryWrapper.in("uid",uidList);
                }else {
                    queryWrapper.like("name",keyword).and(condition->condition.in("uid",uidList));
                }
            }
        }
        List<edu.ysu.backend.entity.dto.Customer> customers = customerMapper.selectList(queryWrapper.last("LIMIT 6"));
        List<Customer> menue=new ArrayList<>();
        if (!customers.isEmpty()){
            for (edu.ysu.backend.entity.dto.Customer customer : customers) {
                menue.add(Customer.builder().label(customer.getName()).value(customer.getUid()).build());
            }
        }
        return Result.ok("查询客户菜单成功").data("customerMenue",menue);
    }
}
