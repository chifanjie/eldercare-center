package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.ysu.backend.entity.dto.*;
import edu.ysu.backend.entity.pojo.OnlineContext;
import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Content;
import edu.ysu.backend.entity.vo.Level;
import edu.ysu.backend.entity.vo.LevelContent;
import edu.ysu.backend.mapper.*;
import edu.ysu.backend.service.NurseService;
import edu.ysu.backend.util.ExcelUtil;
import edu.ysu.backend.util.JwtUtil;
import edu.ysu.backend.entity.excel.NurseRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class NurseServiceImpl implements NurseService {

    @Autowired
    private NurseLevelMapper nurseLevelMapper;
    @Autowired
    private NurseContentMapper nurseContentMapper;
    @Autowired
    private LevelContentMapper levelContentMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private NurseRecordMapper nurseRecordMapper;
    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getNurseLevelPaged(String keyword, int pageNum, int pageSize) {
        Page<NurseLevel> page=new Page<>(pageNum,pageSize);
        QueryWrapper<NurseLevel> queryWrapper=new QueryWrapper<>();
        if ("".equals(keyword)||keyword==null){
            queryWrapper=queryWrapper.notIn("del",1);
        }else {
            queryWrapper.like("level",keyword).and(condition->condition.notIn("del",1));
        }
        IPage<NurseLevel> iPage=nurseLevelMapper.selectPage(page,queryWrapper);
        return Result.ok("查询护理级别成功").data("recordP",iPage);
    }

    @Override
    public Result getNurseContentPaged(String keyword, int pageNum, int pageSize) {
        Page<NurseContent> page=new Page<>(pageNum,pageSize);
        QueryWrapper<NurseContent> queryWrapper=new QueryWrapper<>();
        if ("".equals(keyword)||keyword==null){
            queryWrapper=queryWrapper.notIn("del",1);
        }else {
            queryWrapper.like("name",keyword).or()
                    .like("no",keyword)
                    .and(condition->condition.notIn("del",1));
        }
        IPage<NurseContent> iPage=nurseContentMapper.selectPage(page,queryWrapper);
        return Result.ok("查询护理内容成功").data("recordP",iPage);
    }

    @Override
    public Result getNurseContentSetPaged(String keyword,int pageNum, int pageSize) {
        Page<LevelContentContrast> page=new Page<>(pageNum,pageSize);
        QueryWrapper<LevelContentContrast> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("nid",keyword).orderByAsc("sort");
        IPage<LevelContentContrast> ipage=levelContentMapper.selectPage(page,queryWrapper);
        List<LevelContentContrast> record=ipage.getRecords();
        for (LevelContentContrast contrast:record){
            QueryWrapper<NurseContent> query=new QueryWrapper<>();
            query.eq("cid",contrast.getCid());
            NurseContent content=nurseContentMapper.selectOne(query);
            contrast.setContent(content.getName());
        }
        return Result.ok("查询护理内容配置成功").data("recordP",ipage);
    }

    @Override
    public Result addNewNurseLevel(Level level) {
        NurseLevel nurseLevel=NurseLevel.builder()
                .level(level.getLevel())
                .statue(Integer.valueOf(level.getStatue()))
                .memo(level.getMemo()).build();
        int insert = nurseLevelMapper.insert(nurseLevel);
        return insert>0?Result.ok("添加护理级别成功"):Result.fail("添加护理级别失败");
    }

    @Override
    public Result changeNurseLevel(Level level) {
        QueryWrapper<NurseLevel> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("nid",level.getNid());
        NurseLevel nurseLevel=NurseLevel.builder()
                .statue(Integer.valueOf(level.getStatue()))
                .memo(level.getMemo())
                .level(level.getLevel()).build();
        int update = nurseLevelMapper.update(nurseLevel, queryWrapper);
        return update>0?Result.ok("修改护理级别成功"):Result.fail("修改护理级别失败");
    }

    @Override
    public Result deleteNurseLevel(Level level) {
        QueryWrapper<NurseLevel> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("nid",level.getNid());
        NurseLevel nurseLevel=NurseLevel.builder().del(1).build();
        int delete = nurseLevelMapper.update(nurseLevel, queryWrapper);
        return delete>0?Result.ok("删除护理级别成功"):Result.fail("删除护理级别失败");
    }

    @Override
    public Result addNewNurseContent(Content content) {
        NurseContent nurseContent=NurseContent.builder()
                .no(content.getNo())
                .name(content.getName())
                .description(content.getDescription())
                .price(content.getPrice())
                .value(Integer.valueOf(content.getValue()))
                .statue(Integer.valueOf(content.getStatue()))
                .memo(content.getMemo()).build();
        int insert = nurseContentMapper.insert(nurseContent);
        return insert>0?Result.ok("添加护理内容成功"):Result.fail("添加护理内容失败");
    }

    @Override
    public Result changeNurseContent(Content content) {
        QueryWrapper<NurseContent> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("cid",content.getCid());
        NurseContent nurseContent=NurseContent.builder()
                .no(content.getNo())
                .name(content.getName())
                .description(content.getDescription())
                .price(content.getPrice())
                .value(Integer.valueOf(content.getValue()))
                .statue(Integer.valueOf(content.getStatue()))
                .memo(content.getMemo()).build();
        int update = nurseContentMapper.update(nurseContent, queryWrapper);
        return update>0?Result.ok("修改护理内容成功"):Result.fail("修改护理内容失败");
    }

    @Override
    public Result deleteNurseContent(Content content) {
        QueryWrapper<NurseContent> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("cid",content.getCid());
        NurseContent nurseContent=NurseContent.builder().del(1).build();
        int delete = nurseContentMapper.update(nurseContent, queryWrapper);
        return delete>0?Result.ok("删除护理内容成功"):Result.fail("删除护理内容失败");
    }

    @Override
    public Result addNurseContentSet(LevelContent levelContent) {
        QueryWrapper<LevelContentContrast> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("nid",levelContent.getNid());
        List<LevelContentContrast> list=levelContentMapper.selectList(queryWrapper);
        for (LevelContentContrast obj:list){
            if (obj.getCid().equals(levelContent.getCid())){
                return Result.fail("该护理内容已存在");
            }
        }
        LevelContentContrast levelContentContrast=LevelContentContrast.builder()
                .cid(levelContent.getCid())
                .nid(levelContent.getNid())
                .cycle(levelContent.getCycle())
                .frequency(levelContent.getFrequency())
                .sort(levelContent.getSort())
                .memo(levelContent.getMemo()).build();
        int insert = levelContentMapper.insert(levelContentContrast);
        return insert>0?Result.ok("添加护理内容配置成功"):Result.fail("添加护理内容配置失败");
    }

    @Override
    public Result deleteNurseContentSet(LevelContent levelContent) {
        Integer sort=1;
        QueryWrapper<LevelContentContrast> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("nid",levelContent.getNid()).and(condition->condition.eq("cid",levelContent.getCid()));
        int delete = levelContentMapper.delete(queryWrapper);
        QueryWrapper<LevelContentContrast> query=new QueryWrapper<>();
        query.eq("nid",levelContent.getNid());
        List<LevelContentContrast> list=levelContentMapper.selectList(query);
        log.info("Operation event recorded");
        for (LevelContentContrast obj:list){
            QueryWrapper<LevelContentContrast> updateQuery=new QueryWrapper<>();
            LevelContentContrast levelContentContrast=LevelContentContrast.builder()
                    .cycle(obj.getCycle())
                    .frequency(obj.getFrequency())
                    .memo(obj.getMemo())
                    .sort(sort).build();
            updateQuery.eq("nid",obj.getNid()).and(condition->condition.eq("cid",obj.getCid()));
            int update = levelContentMapper.update(levelContentContrast, updateQuery);
            sort++;
        }
        return delete>0?Result.ok("删除护理内容配置成功"):Result.fail("删除护理内容配置失败");
    }

    @Override
    public Result changeNurseContentSet(LevelContent levelContent) {
        log.info("Operation event recorded");
        QueryWrapper<LevelContentContrast> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("nid",levelContent.getNid()).and(condition->condition.notIn("cid",levelContent.getDefaultId()));
        List<LevelContentContrast> list=levelContentMapper.selectList(queryWrapper);
        for (LevelContentContrast obj:list){
            if (obj.getCid().equals(levelContent.getCid())){
                return Result.fail("该护理内容已存在");
            }
        }
        LevelContentContrast levelContentContrast=LevelContentContrast.builder()
                .cid(levelContent.getCid())
                .cycle(levelContent.getCycle())
                .frequency(levelContent.getFrequency())
                .sort(levelContent.getSort())
                .memo(levelContent.getMemo()).build();
        QueryWrapper<LevelContentContrast> updateQuery=new QueryWrapper<>();
        updateQuery.eq("nid",levelContent.getNid()).and(condition->condition.eq("cid",levelContent.getCid()));
        int update = levelContentMapper.update(levelContentContrast, updateQuery);
        return update>0?Result.ok("修改护理内容配置成功"):Result.fail("修改护理内容配置失败");
    }

    @Override
    public Result getCustomerPaged(String keyword, int pageNum, int pageSize) {
        String current = OnlineContext.getCurrent();
        String email = JwtUtil.parseJWT(current).getSubject();
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("email",email));
        Page<Customer> page=new Page<>(pageNum,pageSize);
        if (user.getRole()!=4){
            QueryWrapper<Customer> queryWrapper=new QueryWrapper<>();
            if (keyword==null||"".equals(keyword)){
                queryWrapper.eq("del",0);
            }else {
                queryWrapper.like("name",keyword).and(condition->condition.eq("del",0));
            }
            IPage<Customer> customerIPage = customerMapper.selectPage(page, queryWrapper);
            for (Customer customer : customerIPage.getRecords()){
                Building building=buildingMapper.selectCustomerInWhere(customer.getUid());
                customer.setBedInfo(building.getRoomName()+"-"+building.getBedName());
            }
            return Result.ok("查询客户数据成功").data("recordP",customerIPage);
        }else {
            List<Integer> uidList=serviceMapper.selectSupportProvider(user.getUid());
            QueryWrapper<Customer> queryWrapper=new QueryWrapper<>();
            if (!uidList.isEmpty()) {
                if (keyword == null || "".equals(keyword)) {
                    queryWrapper.in("uid", uidList);
                } else {
                    queryWrapper.like("name", keyword)
                            .and(condition -> condition.in("uid", uidList));
                }
            }else {
                queryWrapper.eq("uid",-1);
            }
            IPage<Customer> customerIPage = customerMapper.selectPage(page, queryWrapper);
            if (!customerIPage.getRecords().isEmpty()){
                for (Customer customer : customerIPage.getRecords()){
                    Building building=buildingMapper.selectCustomerInWhere(customer.getUid());
                    customer.setBedInfo(building.getRoomName()+"-"+building.getBedName());
                }
            }
            return Result.ok("查询客户数据成功").data("recordP",customerIPage);
        }
    }

    @Override
    public Result getNurseRecordPaged(String keyword, int pageNum, int pageSize) {
        String current = OnlineContext.getCurrent();
        String email = JwtUtil.parseJWT(current).getSubject();
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("email",email));
        Page<edu.ysu.backend.entity.dto.NurseRecord> page=new Page<>(pageNum,pageSize);
        IPage<edu.ysu.backend.entity.dto.NurseRecord> iPage;
        if (user.getRole()!=4){
            QueryWrapper<edu.ysu.backend.entity.dto.NurseRecord> queryWrapper=new QueryWrapper<>();
            if (keyword==null||"".equals(keyword)){
                queryWrapper=null;
            }else {
                QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                customerQueryWrapper.like("name",keyword);
                List<Customer> customerList=customerMapper.selectList(customerQueryWrapper);
                if (customerList.isEmpty()){
                    queryWrapper.eq("uid",-1);
                }else {
                    Set<Integer> uidSet=new HashSet<>();
                    for (Customer customer:customerList){
                        uidSet.add(customer.getUid());
                    }
                    queryWrapper.in("uid",uidSet);
                }
            }
            iPage = nurseRecordMapper.selectPage(page, queryWrapper);
        }else {
            List<Integer> uidList=serviceMapper.selectSupportProvider(user.getUid());
            QueryWrapper<edu.ysu.backend.entity.dto.NurseRecord> queryWrapper=new QueryWrapper<>();
            if (!uidList.isEmpty()){
                if (keyword==null||"".equals(keyword)){
                    queryWrapper.in("uid",uidList)
                            .and(condition->condition.eq("sid",user.getUid()));
                }else {
                    QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                    customerQueryWrapper.like("name",keyword)
                            .and(condition->condition.in("uid",uidList));
                    List<Customer> customerList=customerMapper.selectList(customerQueryWrapper);
                    if (customerList.isEmpty()){
                        queryWrapper.eq("uid",-1);
                    }else {
                        Set<Integer> uidSet=new HashSet<>();
                        for (Customer customer:customerList){
                            uidSet.add(customer.getUid());
                        }
                        queryWrapper.in("uid",uidSet)
                                .and(condition->condition.eq("sid",user.getUid()));
                    }
                }
            }else {
                queryWrapper.eq("uid",-1);
            }
            iPage = nurseRecordMapper.selectPage(page, queryWrapper);
        }
        if (iPage.getRecords().isEmpty()){
            return Result.ok("查询护理记录数据成功").data("recordP",iPage);
        }else {
            for (edu.ysu.backend.entity.dto.NurseRecord nurseRecord : iPage.getRecords()){
                QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                QueryWrapper<Staff> staffQueryWrapper=new QueryWrapper<>();
                QueryWrapper<NurseContent> contentQueryWrapper=new QueryWrapper<>();
                customerQueryWrapper.eq("uid",nurseRecord.getUid());
                staffQueryWrapper.eq("sid",nurseRecord.getSid());
                contentQueryWrapper.eq("cid",nurseRecord.getCid());
                nurseRecord.setCustomerName(customerMapper.selectOne(customerQueryWrapper).getName());
                nurseRecord.setStaffName(staffMapper.selectOne(staffQueryWrapper).getName());
                nurseRecord.setContentName(nurseContentMapper.selectOne(contentQueryWrapper).getName());
            }
        }
        return Result.ok("查询护理记录数据成功").data("recordP",iPage);
    }

    @Override
    public Result addTempNurseRecord(edu.ysu.backend.entity.vo.NurseRecord nurseRecord) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date=LocalDate.now();
        String nowDate=date.format(formatter);
        List<Integer> supportId=serviceMapper.selectSupportId(nurseRecord.getUid());
        if (supportId.isEmpty()){
            return Result.fail("所选客户并没有分配护工，请先分配护工").data("err",500);
        }else {
            QueryWrapper<edu.ysu.backend.entity.dto.NurseRecord> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("uid",nurseRecord.getUid()).and(condition->condition.eq("cid",nurseRecord.getCid()));
            edu.ysu.backend.entity.dto.NurseRecord content=nurseRecordMapper.selectOne(queryWrapper);
            if (content==null){
                edu.ysu.backend.entity.dto.NurseRecord insert= edu.ysu.backend.entity.dto.NurseRecord.builder()
                        .sid(supportId.get((int)(Math.random()*supportId.size())))
                        .uid(nurseRecord.getUid())
                        .cid(nurseRecord.getCid())
                        .time(nurseRecord.getTime())
                        .date(nowDate)
                        .memo(nurseRecord.getMemo()).build();
                int inserted = nurseRecordMapper.insert(insert);
                return inserted>0?Result.ok("设置临时护理项目成功"):Result.fail("设置临时护理项目失败");
            }else {
                edu.ysu.backend.entity.dto.NurseRecord update= edu.ysu.backend.entity.dto.NurseRecord.builder()
                        .nid(content.getNid())
                        .sid(supportId.get((int)(Math.random()*supportId.size())))
                        .uid(nurseRecord.getUid())
                        .cid(nurseRecord.getCid())
                        .time(content.getTime()+nurseRecord.getTime())
                        .date(nowDate)
                        .memo(nurseRecord.getMemo())
                        .build();
                int updated = nurseRecordMapper.updateById(update);
                return updated>0?Result.ok("设置临时护理项目成功"):Result.fail("设置临时护理项目失败");
            }
        }
    }

    @Override
    public void requestRecordSheet(HttpServletResponse response, String begin, String end) {
        QueryWrapper<edu.ysu.backend.entity.dto.NurseRecord> queryWrapper=new QueryWrapper<>();
        queryWrapper.ge("date",begin).le("date",end);
        List<edu.ysu.backend.entity.dto.NurseRecord> records=nurseRecordMapper.selectList(queryWrapper);
        if (records.isEmpty()){
            response.setStatus(404);
        }else {
            for (edu.ysu.backend.entity.dto.NurseRecord nurseRecord:records){
                QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                QueryWrapper<Staff> staffQueryWrapper=new QueryWrapper<>();
                QueryWrapper<NurseContent> contentQueryWrapper=new QueryWrapper<>();
                customerQueryWrapper.eq("uid",nurseRecord.getUid());
                staffQueryWrapper.eq("sid",nurseRecord.getSid());
                contentQueryWrapper.eq("cid",nurseRecord.getCid());
                nurseRecord.setCustomerName(customerMapper.selectOne(customerQueryWrapper).getName());
                nurseRecord.setStaffName(staffMapper.selectOne(staffQueryWrapper).getName());
                nurseRecord.setContentName(nurseContentMapper.selectOne(contentQueryWrapper).getName());
            }
            List<NurseRecord> data=new ArrayList<>();
            int index=1;
            for (edu.ysu.backend.entity.dto.NurseRecord record:records){
                data.add(NurseRecord.builder()
                        .no(index)
                        .customerName(record.getCustomerName())
                        .staffName(record.getStaffName())
                        .serviceName(record.getContentName())
                        .serviceContent(record.getMemo())
                        .serviceTime(record.getTime())
                        .serviceDate(record.getDate()).build());
                index++;
            }
            ExcelUtil.exportExcel(response, NurseRecord.class,data);
        }
    }

    @Override
    public Result addGroupNurseRecord(edu.ysu.backend.entity.vo.NurseRecord nurseRecord) {
        int inserted=0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date=LocalDate.now();
        String nowDate=date.format(formatter);
        for (Integer uid : nurseRecord.getUidList()){
            QueryWrapper<edu.ysu.backend.entity.dto.NurseRecord> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("uid",uid)
                    .and(condition->condition.eq("date",nowDate))
                    .and(condition->condition.eq("strategy",1));
            List<edu.ysu.backend.entity.dto.NurseRecord> exist=nurseRecordMapper.selectList(queryWrapper);
            if (exist.isEmpty()){
                List<Integer> supportId=serviceMapper.selectSupportId(uid);
                if (supportId.isEmpty()){
                    QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                    customerQueryWrapper.eq("uid",uid);
                    return Result.fail(customerMapper.selectOne(customerQueryWrapper).getName()+"没有设置护工，请先设置护工").data("err",500);
                }else {
                    QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                    customerQueryWrapper.eq("uid",uid);
                    QueryWrapper<edu.ysu.backend.entity.dto.Service> serviceQueryWrapper=new QueryWrapper<>();
                    serviceQueryWrapper.eq("uid",uid).last("AND service.residue - service.frequency >= 0");
                    List<edu.ysu.backend.entity.dto.Service> serviceList=serviceMapper.selectList(serviceQueryWrapper);
                    if (serviceList.isEmpty()){
                        return Result.fail(customerMapper.selectOne(customerQueryWrapper).getName()+"没有购买任何护理项目").data("err",500);
                    }else {
                        for (edu.ysu.backend.entity.dto.Service service:serviceList){
                            edu.ysu.backend.entity.dto.NurseRecord insert= edu.ysu.backend.entity.dto.NurseRecord.builder()
                                    .uid(uid)
                                    .sid(supportId.get((int)(Math.random()*supportId.size())))
                                    .cid(service.getCid())
                                    .strategy(1)
                                    .date(nowDate)
                                    .time(service.getFrequency())
                                    .memo(service.getMemo()).build();
                            inserted = nurseRecordMapper.insert(insert);
                            edu.ysu.backend.entity.dto.Service update= edu.ysu.backend.entity.dto.Service.builder()
                                    .sid(service.getSid())
                                    .residue(service.getResidue()-service.getFrequency()).build();
                            serviceMapper.updateById(update);
                        }
                        QueryWrapper<edu.ysu.backend.entity.dto.Service> updateQueryWrapper=new QueryWrapper<>();
                        queryWrapper.eq("uid",uid);
                        List<edu.ysu.backend.entity.dto.Service> services=serviceMapper.selectList(updateQueryWrapper);
                        for (edu.ysu.backend.entity.dto.Service service:services){
                            boolean change=false;
                            int flag=0;
                            if (Objects.equals(service.getResidue(), service.getFrequency())){
                                change=true;
                                flag=1;
                            } else if (service.getResidue()==0) {
                                change=true;
                                flag=2;
                            }
                            if (change){
                                edu.ysu.backend.entity.dto.Service alert = edu.ysu.backend.entity.dto.Service.builder()
                                        .sid(service.getSid())
                                        .statue(flag).build();
                                serviceMapper.updateById(alert);
                            }
                        }
                    }
                }
            }else {
                QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                customerQueryWrapper.eq("uid",uid);
                return Result.fail(customerMapper.selectOne(customerQueryWrapper).getName()+"今日已经安排过护理了").data("err",500);
            }
        }
        return inserted>0?Result.ok("今日护理安排成功"):Result.fail("今日护理安排失败");
    }

    @Override
    public Result getCustomerBuyServicePaged(String keyword, int pageNum, int pageSize) {
        QueryWrapper<edu.ysu.backend.entity.dto.Service> queryWrapper=new QueryWrapper<>();
        Page<edu.ysu.backend.entity.dto.Service> page=new Page<>(pageNum,pageSize);
        queryWrapper.eq("uid",keyword);
        IPage<edu.ysu.backend.entity.dto.Service> iPage=serviceMapper.selectPage(page,queryWrapper);
        for (edu.ysu.backend.entity.dto.Service service: iPage.getRecords()){
            QueryWrapper<NurseContent> contentQueryWrapper=new QueryWrapper<>();
            contentQueryWrapper.eq("cid",service.getCid());
            service.setContentName(nurseContentMapper.selectOne(contentQueryWrapper).getName());
        }
        return Result.ok("查询用户购买护理项目成功").data("recordP",iPage);
    }

    @Override
    public Result deleteCustomerBuyService(edu.ysu.backend.entity.vo.Service service) {
        edu.ysu.backend.entity.dto.Service delete= edu.ysu.backend.entity.dto.Service.builder()
                .sid(service.getSid()).build();
        int deleted = serviceMapper.deleteById(delete);
        return deleted>0?Result.ok("删除用户购买护理项目成功"):Result.fail("删除用户购买护理项目失败");
    }

    @Override
    public Result changeCustomerBuyService(edu.ysu.backend.entity.vo.Service service) {
        edu.ysu.backend.entity.dto.Service change= edu.ysu.backend.entity.dto.Service.builder()
                .sid(service.getSid())
                .frequency(service.getFrequency()).build();
        int updated=serviceMapper.updateById(change);
        return updated>0?Result.ok("修改用户购买护理项目成功"):Result.fail("修改用户购买护理项目失败");
    }
}
