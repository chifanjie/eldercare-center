package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.ysu.backend.entity.dto.*;
import edu.ysu.backend.entity.pojo.OnlineContext;
import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.mapper.*;
import edu.ysu.backend.service.ServicesService;
import edu.ysu.backend.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServicesServiceImpl implements ServicesService {

    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private NurseContentMapper nurseContentMapper;
    @Autowired
    private LevelContentMapper levelContentMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getServiceCustomerPaged(String keyword, int pageNum, int pageSize) {
        String current = OnlineContext.getCurrent();
        String email = JwtUtil.parseJWT(current).getSubject();
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("email",email));
        QueryWrapper<Customer> queryWrapper=new QueryWrapper<>();
        Page<Customer> page=new Page<>(pageNum,pageSize);
        if (user.getRole()==1){
            if (keyword==null||"".equals(keyword)){
                queryWrapper=null;
            }else {
                queryWrapper.like("name",keyword);
            }
        }else {
            List<Integer> uidlist=serviceMapper.selectButlerProvider(user.getUid());
            if (uidlist.isEmpty()){
                queryWrapper.eq("uid",-1);
            }else {
                if (keyword==null||"".equals(keyword)){
                    queryWrapper.in("uid",uidlist);
                }else {
                    queryWrapper.like("name",keyword)
                            .and(condition->condition.in("uid",uidlist));
                }
            }
        }
        IPage<Customer> customerIPage = customerMapper.selectPage(page, queryWrapper);
        return Result.ok("查询服务关注客户记录成功").data("recordP",customerIPage);
    }

    @Override
    public Result getServicePaged(String keyword, int pageNum, int pageSize) {
        String current = OnlineContext.getCurrent();
        String email = JwtUtil.parseJWT(current).getSubject();
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("email",email));
        QueryWrapper<edu.ysu.backend.entity.dto.Service> queryWrapper=new QueryWrapper<>();
        Page<edu.ysu.backend.entity.dto.Service> page=new Page<>(pageNum,pageSize);
        if (user.getRole()==1){
            if (keyword==null||"".equals(keyword)){
                queryWrapper=null;
            }else {
                QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                customerQueryWrapper.like("name",keyword);
                List<Customer> customerList = customerMapper.selectList(customerQueryWrapper);
                Set<Integer> idSet=new HashSet<>();
                if (customerList.isEmpty()){
                    queryWrapper.eq("uid",-1);
                }else {
                    for (Customer customer:customerList){
                        idSet.add(customer.getUid());
                    }
                    queryWrapper.in("uid",idSet);
                }
            }
        }else {
            List<Integer> uidlist=serviceMapper.selectButlerProvider(user.getUid());
            if (uidlist.isEmpty()){
                queryWrapper.eq("uid",-1);
            }else {
                if (keyword==null||"".equals(keyword)){
                    queryWrapper.in("uid",uidlist);
                }else {
                    QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                    customerQueryWrapper.like("name",keyword).and(condition->condition.in("uid",uidlist));
                    List<Customer> customerList = customerMapper.selectList(customerQueryWrapper);
                    Set<Integer> idSet=new HashSet<>();
                    if (customerList.isEmpty()){
                        queryWrapper.eq("uid",-1);
                    }else {
                        for (Customer customer:customerList){
                            idSet.add(customer.getUid());
                        }
                        queryWrapper.in("uid",idSet);
                    }
                }
            }
        }
        IPage<edu.ysu.backend.entity.dto.Service> iPage=serviceMapper.selectPage(page,queryWrapper);
        for (edu.ysu.backend.entity.dto.Service service:iPage.getRecords()){
            QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
            customerQueryWrapper.eq("uid",service.getUid());
            service.setCustomerName(customerMapper.selectOne(customerQueryWrapper).getName());
            service.setContentName(nurseContentMapper.selectContentName(service.getCid()));
        }
        return Result.ok("查询服务关注数据成功").data("recordP",iPage);
    }

    @Override
    public Result addServicePurchaseRecord(edu.ysu.backend.entity.vo.Service service) {
        int inserted=0;
        int updated=0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date=LocalDate.now();
        String nowDate=date.format(formatter);
        QueryWrapper<LevelContentContrast> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("nid",service.getCid());
        List<LevelContentContrast> records=levelContentMapper.selectList(queryWrapper);
        QueryWrapper<edu.ysu.backend.entity.dto.Service> serviceQueryWrapper=new QueryWrapper<>();
        serviceQueryWrapper.eq("uid",service.getUid());
        List<edu.ysu.backend.entity.dto.Service> services = serviceMapper.selectList(serviceQueryWrapper);
        if (services.isEmpty()){
            for (LevelContentContrast contrast:records){
                edu.ysu.backend.entity.dto.Service insert= edu.ysu.backend.entity.dto.Service.builder()
                        .uid(service.getUid())
                        .cid(contrast.getCid())
                        .last(contrast.getFrequency())
                        .num(contrast.getFrequency())
                        .residue(contrast.getFrequency())
                        .buy(nowDate)
                        .memo(service.getMemo())
                        .statue(1)
                        .frequency(contrast.getFrequency()).build();
                inserted=serviceMapper.insert(insert);
            }
            return inserted>0?Result.ok("购买护理服务成功"):Result.fail("购买护理服务失败");
        }else {
            List<Integer> serviceId=new ArrayList<>();
            for (edu.ysu.backend.entity.dto.Service exist:services){
                serviceId.add(exist.getCid());
            }
            for (LevelContentContrast contrast:records){
                if (serviceId.contains(contrast.getCid())){
                    QueryWrapper<edu.ysu.backend.entity.dto.Service> updateQueryWrapper=new QueryWrapper<>();;
                    updateQueryWrapper.eq("uid",service.getUid()).and(condition->condition.eq("cid",contrast.getCid()));
                    edu.ysu.backend.entity.dto.Service select=serviceMapper.selectOne(updateQueryWrapper);
                    edu.ysu.backend.entity.dto.Service update= edu.ysu.backend.entity.dto.Service.builder()
                            .sid(select.getSid())
                            .last(select.getResidue())
                            .num(select.getNum()+contrast.getFrequency())
                            .residue(select.getResidue()+contrast.getFrequency())
                            .buy(nowDate)
                            .memo(service.getMemo()).build();
                    if (select.getResidue()+contrast.getFrequency()>select.getFrequency()){
                        update.setStatue(0);
                    }else {
                        update.setStatue(1);
                    }
                    updated = serviceMapper.updateById(update);
                }else {
                    edu.ysu.backend.entity.dto.Service insert= edu.ysu.backend.entity.dto.Service.builder()
                            .uid(service.getUid())
                            .cid(contrast.getCid())
                            .last(contrast.getFrequency())
                            .num(contrast.getFrequency())
                            .residue(contrast.getFrequency())
                            .buy(nowDate)
                            .statue(1)
                            .memo(service.getMemo()).build();
                    inserted = serviceMapper.insert(insert);
                }
            }
            return updated>0||inserted>0?Result.ok("购买护理服务成功"):Result.fail("购买护理服务失败");
        }
    }

    @Override
    public Result alertServiceRecord(edu.ysu.backend.entity.vo.Service service) {
        if (service.getStatue()>=3){
            return Result.fail("所选项目正处于提醒状态");
        }else {
            edu.ysu.backend.entity.dto.Service update= edu.ysu.backend.entity.dto.Service.builder()
                    .sid(service.getSid())
                    .statue(service.getStatue()+3).build();
            int updated = serviceMapper.updateById(update);
            return updated>0?Result.ok("已提醒"):Result.fail("提醒失败");
        }
    }

    @Override
    public Result addServiceRecordPurchaseRecord(edu.ysu.backend.entity.vo.Service service) {
        QueryWrapper<edu.ysu.backend.entity.dto.Service> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid",service.getSid());
        edu.ysu.backend.entity.dto.Service select= serviceMapper.selectOne(queryWrapper);
        edu.ysu.backend.entity.dto.Service update= edu.ysu.backend.entity.dto.Service.builder()
                .sid(service.getSid())
                .last(select.getResidue())
                .num(select.getNum()+service.getNum())
                .residue(select.getResidue()+service.getNum()).build();
        if (select.getResidue()+service.getNum()>select.getFrequency()){
            update.setStatue(0);
        }else if (select.getFrequency()==select.getResidue()+service.getNum()){
            update.setStatue(1);
        }
        int updated = serviceMapper.updateById(update);
        return updated>0?Result.ok("购买成功"):Result.fail("购买失败");
    }

    @Override
    public Result cleanServiceAlert() {
        int updated=0;
        QueryWrapper<edu.ysu.backend.entity.dto.Service> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("statue",3);
        List<edu.ysu.backend.entity.dto.Service> services = serviceMapper.selectList(queryWrapper);
        for (edu.ysu.backend.entity.dto.Service service : services) {
            edu.ysu.backend.entity.dto.Service update= edu.ysu.backend.entity.dto.Service.builder()
                    .sid(service.getSid())
                    .statue(service.getStatue()-3).build();
            updated = serviceMapper.updateById(update);
        }
        return updated>0?Result.ok("清理提醒标记成功"):Result.fail("清理提醒标记失败");
    }

    @Override
    public Result getHealthCareData(Integer uid) {
        QueryWrapper<edu.ysu.backend.entity.dto.Service> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        List<edu.ysu.backend.entity.dto.Service> services=serviceMapper.selectList(queryWrapper);
        List<NurseContent> nurseContents=new ArrayList<>();
        for (edu.ysu.backend.entity.dto.Service service : services){
            QueryWrapper<NurseContent> nurseQueryWrapper=new QueryWrapper<>();
            nurseQueryWrapper.eq("cid",service.getCid());
            nurseContents.add(nurseContentMapper.selectOne(nurseQueryWrapper));
        }
        return Result.ok("查询护理服务成功").data("nurseContents",nurseContents);
    }

    @Override
    public Result getCustomerRecord(Integer uid) {
        QueryWrapper<Customer> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        Customer customer=customerMapper.selectOne(queryWrapper);
        Building building=buildingMapper.selectCustomerInWhere(uid);
        customer.setBuildingObj(building);
        return Result.ok("查询客户记录成功").data("customer",customer);
    }

    @Override
    public Result getServiceSetPaged(String keyword, int pageNum, int pageSize) {
        Page<Staff> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Staff> queryWrapper=new QueryWrapper<>();
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("role",6);
        List<User> userList=userMapper.selectList(userQueryWrapper);
        List<Integer> userId = userList.stream().map(User::getUid).collect(Collectors.toList());
        if (keyword==null||"".equals(keyword)){
            queryWrapper.in("sid",userId);
        }else {
            queryWrapper.in("sid",userId).and(condition->condition.like("name",keyword));
        }
        IPage<Staff> iPage=staffMapper.selectPage(page,queryWrapper);
        for (Staff staff:iPage.getRecords()){
            staff.setServiceName(serviceMapper.selectServiceName(staff.getSid()));
        }
        return Result.ok("查询健康管家记录成功").data("recordP",iPage);
    }

    @Override
    public Result deleteServiceSet(edu.ysu.backend.entity.vo.Service service) {
        int delete = serviceMapper.deleteServiceSet(service.getSid());
        return delete>0?Result.ok("删除成功"):Result.fail("删除失败,该管家不存在服务对象");
    }

    @Override
    public Result getServiceSetInfo(Integer sid) {
        List<Integer> serviceSet = serviceMapper.selectServiceId(sid);
        return Result.ok("查询服务对象成功").data("serviceSet",serviceSet);
    }

    @Override
    public Result changeServiceSet(edu.ysu.backend.entity.vo.Staff staff) {
        int updated=0;
        serviceMapper.deleteServiceSet(staff.getSid());
        if (staff.getUid().isEmpty()){
            updated=1;
        }else {
            for (Integer uid:staff.getUid()){
                updated=serviceMapper.insertServiceSet(staff.getSid(),uid);
                if (updated==0){
                    break;
                }
            }
        }
        return updated>0?Result.ok("设置成功"):Result.fail("设置失败");
    }
}
