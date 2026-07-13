package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.ysu.backend.entity.pojo.OnlineContext;
import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.mapper.*;
import edu.ysu.backend.service.CustomerService;
import edu.ysu.backend.util.JwtUtil;
import edu.ysu.backend.entity.dto.Staff;
import edu.ysu.backend.entity.dto.User;
import edu.ysu.backend.entity.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private CheckoutMapper checkoutMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private BedMapper bedMapper;
    @Autowired
    private EgressMapper egressMapper;
    @Autowired
    private MealManageMapper mealManageMapper;
    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private FamilySetMapper familySetMapper;
    @Autowired
    private EmergencySetMapper emergencySetMapper;

    @Override
    public Result getCustomerPaged(String keyword, String round, int pageNum, int pageSize) {
        Page<edu.ysu.backend.entity.dto.Customer> page = new Page<>(pageNum, pageSize);
        QueryWrapper<edu.ysu.backend.entity.dto.Customer> queryWrapper = new QueryWrapper<>();
        if ("".equals(keyword)||keyword==null){
            queryWrapper.eq("statue",Integer.parseInt(round))
                    .and(condition->condition.eq("del",0));
        }else {
            queryWrapper.like("name",keyword)
                    .and(condition->condition.eq("statue",Integer.parseInt(round)))
                    .and(condition->condition.eq("del",0));
        }
        IPage<edu.ysu.backend.entity.dto.Customer> iPage=customerMapper.selectPage(page,queryWrapper);
        for (edu.ysu.backend.entity.dto.Customer customer:iPage.getRecords()){
            edu.ysu.backend.entity.dto.Building building=buildingMapper.selectCustomerInWhere(customer.getUid());
            customer.setRoom(building.getRoomName());
            customer.setBuilding(building.getName());
            customer.setSupport(serviceMapper.selectSupportName(customer.getUid()));
            customer.setButler(serviceMapper.selectButlerByUid(customer.getUid()));
            customer.setFamilies(customerMapper.selectFamilyByUidLimited(customer.getUid()));
        }
        return Result.ok("查询入住登记信息成功").data("recordP",iPage);
    }

    @Override
    public Result addCheckin(Customer customer) {
        Integer eid= Integer.valueOf(customer.getBuilding().substring(customer.getBuilding().indexOf("-")+1));
        QueryWrapper<edu.ysu.backend.entity.dto.Bed> bedQueryWrapper=new QueryWrapper<>();
        bedQueryWrapper.eq("eid",eid);
        Integer statue = bedMapper.selectOne(bedQueryWrapper).getStatue();
        if (statue!=0){
            return Result.fail("该床位已经有人了").data("err",500);
        }else {
            edu.ysu.backend.entity.dto.Customer insert= edu.ysu.backend.entity.dto.Customer.builder()
                    .name(customer.getName())
                    .gander(customer.getGander())
                    .age(customer.getAge())
                    .id(customer.getId())
                    .checkin(customer.getCheckin())
                    .expiration(customer.getExpiration())
                    .tel(customer.getTel())
                    .statue(Integer.valueOf(customer.getStatue()))
                    .memo(customer.getMemo())
                    .build();
            int in = customerMapper.insert(insert);
            if (in>0){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateStr=LocalDate.parse(customer.getExpiration(),formatter);
                edu.ysu.backend.entity.dto.Customer file= edu.ysu.backend.entity.dto.Customer.builder()
                        .file("KDHA"+String.valueOf(dateStr.getYear())+String.valueOf(dateStr.getMonthValue())+String.valueOf(dateStr.getDayOfMonth())+"0"+String.valueOf(insert.getUid()))
                        .build();
                QueryWrapper<edu.ysu.backend.entity.dto.Customer> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("uid",insert.getUid());
                bedMapper.update(edu.ysu.backend.entity.dto.Bed.builder().statue(1).build(), new QueryWrapper<edu.ysu.backend.entity.dto.Bed>().eq("eid", eid));
                int update=customerMapper.update(file,queryWrapper);
                int cid=buildingMapper.selectContrast(eid);
                Integer inserted = buildingMapper.insertIntoCustomerContact(insert.getUid(), cid);
                mealManageMapper.insert(edu.ysu.backend.entity.dto.MealManage.builder().uid(insert.getUid()).build());
                return update>0&&inserted>0?Result.ok("插入客户信息成功"):Result.fail("插入客户信息失败");
            }else {
                return Result.fail("插入客户信息失败");
            }
        }
    }

    @Override
    public Result deleteCheckin(Customer customer) {
        edu.ysu.backend.entity.dto.Customer delete= edu.ysu.backend.entity.dto.Customer.builder().del(1).build();
        int update = customerMapper.update(delete, new QueryWrapper<edu.ysu.backend.entity.dto.Customer>().eq("uid", customer.getUid()));
        return update>0?Result.ok("删除入住登记成功"):Result.fail("删除入住登记失败");
    }

    @Override
    public Result getCustomerPosition(Integer uid) {
        edu.ysu.backend.entity.dto.Building building=buildingMapper.selectBuildingNoByUid(uid);
        return Result.ok("获取床位编号信息成功").data("building",building.getRid()+"-"+building.getEid());
    }

    @Override
    public Result changeCustomerCheckin(Customer customer) {
        Integer eid= Integer.valueOf(customer.getBuilding().substring(customer.getBuilding().indexOf("-")+1));
        Map<String, Object> build = getCustomerPosition(customer.getUid()).getData();
        String originBuilding= (String) build.get("building");
        edu.ysu.backend.entity.dto.Customer update;
        if (originBuilding.equals(customer.getBuilding())){
            update= edu.ysu.backend.entity.dto.Customer.builder()
                    .uid(customer.getUid())
                    .name(customer.getName())
                    .gander(customer.getGander())
                    .age(customer.getAge())
                    .id(customer.getId())
                    .checkin(customer.getCheckin())
                    .expiration(customer.getExpiration())
                    .tel(customer.getTel())
                    .statue(Integer.valueOf(customer.getStatue()))
                    .memo(customer.getMemo())
                    .build();
        }else {
            QueryWrapper<edu.ysu.backend.entity.dto.Bed> bedQueryWrapper=new QueryWrapper<>();
            bedQueryWrapper.eq("eid",eid);
            Integer statue = bedMapper.selectOne(bedQueryWrapper).getStatue();
            if (statue!=0){
                return Result.fail("该床位已经有人了").data("err",500);
            }else {
                update= edu.ysu.backend.entity.dto.Customer.builder()
                        .uid(customer.getUid())
                        .name(customer.getName())
                        .gander(customer.getGander())
                        .age(customer.getAge())
                        .id(customer.getId())
                        .checkin(customer.getCheckin())
                        .expiration(customer.getExpiration())
                        .tel(customer.getTel())
                        .statue(Integer.valueOf(customer.getStatue()))
                        .memo(customer.getMemo())
                        .build();
            }
        }
        edu.ysu.backend.entity.dto.Building beforeBuilding =buildingMapper.selectBuildingNoByUid(customer.getUid());
        edu.ysu.backend.entity.dto.Bed beforeBed = bedMapper.selectOne(new QueryWrapper<edu.ysu.backend.entity.dto.Bed>().eq("eid",beforeBuilding.getEid()));
        edu.ysu.backend.entity.dto.Bed afterBed = bedMapper.selectOne(new QueryWrapper<edu.ysu.backend.entity.dto.Bed>().eq("eid",eid));
        edu.ysu.backend.entity.dto.Bed beforeUpdate= edu.ysu.backend.entity.dto.Bed.builder()
                .eid(beforeBed.getEid())
                .statue(afterBed.getStatue()).build();
        edu.ysu.backend.entity.dto.Bed afterUpdate= edu.ysu.backend.entity.dto.Bed.builder()
                .eid(afterBed.getEid())
                .statue(beforeBed.getStatue()).build();
        bedMapper.updateById(beforeUpdate);
        bedMapper.updateById(afterUpdate);
        int cid=buildingMapper.selectContrast(eid);
        Integer updateContact = buildingMapper.updateContact(cid, customer.getUid());
        int recordUpdate = customerMapper.updateById(update);
        return recordUpdate>0&&updateContact>0?Result.ok("修改入住登记成功"):Result.fail("修改入住登记失败");
    }

    @Override
    public Result getCheckoutPaged(String keyword, int pageNum, int pageSize) {
        QueryWrapper<edu.ysu.backend.entity.dto.Checkout> queryWrapper=new QueryWrapper<>();
        Page<edu.ysu.backend.entity.dto.Checkout> page=new Page<>(pageNum,pageSize);
        if("".equals(keyword)||keyword==null){
            queryWrapper=null;
        }else {
            QueryWrapper<edu.ysu.backend.entity.dto.Customer> customerQueryWrapper=new QueryWrapper<>();
            customerQueryWrapper.like("name",keyword).and(condition->condition.eq("del",0));
            List<edu.ysu.backend.entity.dto.Customer> customerList=customerMapper.selectList(customerQueryWrapper);
            Set<Integer> uidList=new HashSet<>();
            if (!customerList.isEmpty()){
                for (edu.ysu.backend.entity.dto.Customer customer:customerList){
                    uidList.add(customer.getUid());
                }
                queryWrapper.in("uid",uidList);
            }else {
                queryWrapper.eq("uid",-1);
            }
        }
        IPage<edu.ysu.backend.entity.dto.Checkout> iPage=checkoutMapper.selectPage(page,queryWrapper);
        for (edu.ysu.backend.entity.dto.Checkout checkout:iPage.getRecords()){
            QueryWrapper<edu.ysu.backend.entity.dto.Customer> customerQueryWrapper=new QueryWrapper<>();
            customerQueryWrapper.eq("uid",checkout.getUid());
            QueryWrapper<Staff> staffQueryWrapper=new QueryWrapper<>();
            if (checkout.getReviewer()!=null){
                staffQueryWrapper.eq("sid",checkout.getReviewer());
                checkout.setReviewerName(staffMapper.selectOne(staffQueryWrapper).getName());
            }
            edu.ysu.backend.entity.dto.Customer customer=customerMapper.selectOne(customerQueryWrapper);
            checkout.setName(customer.getName());
            checkout.setGander(customer.getGander());
            checkout.setAge(customer.getAge());
            checkout.setFile(customer.getFile());
            checkout.setCheckin(customer.getCheckin());
        }
        return Result.ok("查询退住登记信息成功").data("recordP",iPage);
    }

    @Override
    public Result addCheckoutRecord(Checkout checkout) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date=LocalDate.now();
        String nowDate=date.format(formatter);
        QueryWrapper<edu.ysu.backend.entity.dto.Checkout> checkoutQueryWrapper=new QueryWrapper<>();
        checkoutQueryWrapper.eq("uid",checkout.getUid()).and(condition->condition.eq("statue",0));
        edu.ysu.backend.entity.dto.Checkout exist=checkoutMapper.selectOne(checkoutQueryWrapper);
        if (exist==null){
            edu.ysu.backend.entity.dto.Checkout insert= edu.ysu.backend.entity.dto.Checkout.builder()
                    .uid(checkout.getUid())
                    .checkout(checkout.getCheckout())
                    .reason(checkout.getReason())
                    .type(checkout.getType())
                    .apply(nowDate)
                    .memo(checkout.getMemo()).build();
            int inserted = checkoutMapper.insert(insert);
            return inserted>0?Result.ok("添加退住登记信息成功"):Result.fail("添加退住登记信息失败");
        }else {
            return Result.fail("该客户存在未审批的退住登记信息");
        }
    }

    @Override
    public Result deleteCheckout(Checkout checkout) {
        QueryWrapper<edu.ysu.backend.entity.dto.Checkout> checkoutQueryWrapper=new QueryWrapper<>();
        checkoutQueryWrapper.eq("cid",checkout.getCid());
        Integer statue=checkoutMapper.selectOne(checkoutQueryWrapper).getStatue();
        if (statue!=0){
            return Result.fail("只有在登记记录未审批时才能删除记录").data("code",500);
        }else {
            int delete = checkoutMapper.deleteById(edu.ysu.backend.entity.dto.Checkout.builder().cid(checkout.getCid()).build());
            return delete>0?Result.ok("删除退住登记信息成功"):Result.fail("删除退住登记信息失败");
        }
    }

    @Override
    public Result approveCheckout(Checkout checkout) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date=LocalDate.now();
        String nowDate=date.format(formatter);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        String email = JwtUtil.parseJWT(OnlineContext.getCurrent()).getSubject();
        queryWrapper.eq("email",email);
        Integer uid=userMapper.selectOne(queryWrapper).getUid();
        edu.ysu.backend.entity.dto.Checkout update= edu.ysu.backend.entity.dto.Checkout.builder()
                .cid(checkout.getCid())
                .statue(checkout.getStatue())
                .reviewer(uid)
                .review(nowDate)
                .comment(checkout.getMemo()).build();
        if (checkout.getStatue()==1){
            edu.ysu.backend.entity.dto.Customer customer= edu.ysu.backend.entity.dto.Customer.builder()
                    .uid(checkout.getUid())
                    .del(1).build();
            customerMapper.updateById(customer);
            int updatedCheckout = checkoutMapper.updateById(update);
            edu.ysu.backend.entity.dto.Building contract =buildingMapper.selectBuildingNoByUid(checkout.getUid());
            edu.ysu.backend.entity.dto.Bed bedUpdate= edu.ysu.backend.entity.dto.Bed.builder()
                    .eid(contract.getEid())
                    .statue(0).build();
            bedMapper.updateById(bedUpdate);
            buildingMapper.deleteContact(checkout.getUid(),contract.getCid());
            return updatedCheckout>0?Result.ok("审批退住登记信息成功"):Result.fail("审批退住登记信息失败");
        }else {
            int updated = checkoutMapper.updateById(update);
            return updated>0?Result.ok("审批退住登记信息成功"):Result.fail("审批退住登记信息失败");
        }
    }

    @Override
    public Result changeCheckout(Checkout checkout) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date=LocalDate.now();
        String nowDate=date.format(formatter);
        edu.ysu.backend.entity.dto.Checkout updated= edu.ysu.backend.entity.dto.Checkout.builder()
                .cid(checkout.getCid())
                .uid(checkout.getUid())
                .checkout(checkout.getCheckout())
                .reason(checkout.getReason())
                .type(checkout.getType())
                .apply(nowDate)
                .memo(checkout.getMemo()).build();
        int update = checkoutMapper.updateById(updated);
        return update>0?Result.ok("修改退住登记信息成功"):Result.fail("修改退住登记信息失败");
    }

    @Override
    public Result getCustomerEgressPaged(String keyword, int pageNum, int pageSize) {
        QueryWrapper<edu.ysu.backend.entity.dto.Egress> queryWrapper=new QueryWrapper<>();
        Page<edu.ysu.backend.entity.dto.Egress> page=new Page<>(pageNum,pageSize);
        if (keyword==null||"".equals(keyword)){
            queryWrapper=null;
        }else {
            QueryWrapper<edu.ysu.backend.entity.dto.Customer> customerQueryWrapper=new QueryWrapper<>();
            customerQueryWrapper.like("name",keyword).and(condition->condition.eq("del",0));
            List<edu.ysu.backend.entity.dto.Customer> customerList=customerMapper.selectList(customerQueryWrapper);
            Set<Integer> uidList=new HashSet<>();
            if (!customerList.isEmpty()){
                for (edu.ysu.backend.entity.dto.Customer customer:customerList){
                    uidList.add(customer.getUid());
                }
                queryWrapper.in("uid",uidList);
            }else {
                queryWrapper.eq("uid",-1);
            }
        }
        IPage<edu.ysu.backend.entity.dto.Egress> iPage=egressMapper.selectPage(page,queryWrapper);
        for (edu.ysu.backend.entity.dto.Egress egress: iPage.getRecords()){
            QueryWrapper<edu.ysu.backend.entity.dto.Customer> customerQueryWrapper=new QueryWrapper<>();
            customerQueryWrapper.eq("uid",egress.getUid());
            edu.ysu.backend.entity.dto.Customer customer=customerMapper.selectOne(customerQueryWrapper);
            egress.setName(customer.getName());
            egress.setGander(customer.getGander());
            egress.setAge(customer.getAge());
            egress.setFile(customer.getFile());
            QueryWrapper<Staff> userQueryWrapper=new QueryWrapper<>();
            userQueryWrapper.eq("sid",egress.getId());
            egress.setRelationName(staffMapper.selectOne(userQueryWrapper).getName());
            QueryWrapper<Staff> approveQueryWrapper=new QueryWrapper<>();
            approveQueryWrapper.eq("sid",egress.getApprover());
            Staff approver=staffMapper.selectOne(approveQueryWrapper);
            if (approver==null){
                egress.setApproverName("");
            }else {
                egress.setApproverName(approver.getName());
            }
        }
        return Result.ok("查询外出登记信息成功").data("recordP",iPage);
    }

    @Override
    public Result approveEgress(List<Egress> egresses) {
        int up=0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date=LocalDate.now();
        String nowDate=date.format(formatter);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        String email = JwtUtil.parseJWT(OnlineContext.getCurrent()).getSubject();
        queryWrapper.eq("email",email);
        Integer uid=userMapper.selectOne(queryWrapper).getUid();
        for (Egress egress:egresses){
            edu.ysu.backend.entity.dto.Egress update= edu.ysu.backend.entity.dto.Egress.builder()
                    .eid(egress.getEid())
                    .statue(egress.getStatue())
                    .approve(nowDate)
                    .approver(uid).build();
            if (egress.getStatue()==1){
                edu.ysu.backend.entity.dto.Building contract =buildingMapper.selectBuildingNoByUid(egress.getUid());
                edu.ysu.backend.entity.dto.Bed bedUpdate= edu.ysu.backend.entity.dto.Bed.builder()
                        .eid(contract.getEid())
                        .statue(2).build();
                bedMapper.updateById(bedUpdate);
            }
            up = egressMapper.updateById(update);
        }
        return up>0?Result.ok("审批成功"):Result.fail("审批失败");
    }

    @Override
    public Result deleteEgress(Egress egress) {
        QueryWrapper<edu.ysu.backend.entity.dto.Egress> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("eid",egress.getEid());
        Integer statue = egressMapper.selectOne(queryWrapper).getStatue();
        if (statue==0){
            int delete = egressMapper.deleteById(edu.ysu.backend.entity.dto.Egress.builder().eid(egress.getEid()).build());
            return delete>0?Result.ok("删除成功"):Result.fail("删除失败");
        }else {
            return Result.fail("只有在登记记录未审批时才能删除记录").data("code",500);
        }
    }

    @Override
    public Result addEgressRecord(Egress egress) {
        QueryWrapper<edu.ysu.backend.entity.dto.Egress> egressQueryWrapper=new QueryWrapper<>();
        egressQueryWrapper.eq("uid",egress.getUid())
                .and(condition->condition.eq("statue",0)
                        .or(require->require.eq("statue",1)
                                .and(request->request.isNull("really"))));
        if (egressMapper.selectOne(egressQueryWrapper)!=null){
            return Result.fail("该用户已有未审批或未登记回院时间的记录，请先审批或登记");
        }else {
            edu.ysu.backend.entity.dto.Egress insert= edu.ysu.backend.entity.dto.Egress.builder()
                    .uid(egress.getUid())
                    .reason(egress.getReason())
                    .egress(egress.getEgress())
                    .predict(egress.getPredict())
                    .id(egress.getId())
                    .relation(egress.getRelation())
                    .tel(egress.getTel())
                    .memo(egress.getMemo()).build();
            int add = egressMapper.insert(insert);
            return add>0?Result.ok("添加成功"):Result.fail("添加失败");
        }
    }

    @Override
    public Result changeEgress(Egress egress) {
        edu.ysu.backend.entity.dto.Egress update= edu.ysu.backend.entity.dto.Egress.builder()
                .eid(egress.getEid())
                .uid(egress.getUid())
                .reason(egress.getReason())
                .egress(egress.getEgress())
                .predict(egress.getPredict())
                .id(egress.getId())
                .relation(egress.getRelation())
                .tel(egress.getTel())
                .memo(egress.getMemo()).build();
        int updated = egressMapper.updateById(update);
        return updated>0?Result.ok("修改成功"):Result.fail("修改失败");
    }

    @Override
    public Result addEgressReallyRecord(Egress egress) {
        edu.ysu.backend.entity.dto.Egress update= edu.ysu.backend.entity.dto.Egress.builder()
                .eid(egress.getEid())
                .really(egress.getReally()).build();
        edu.ysu.backend.entity.dto.Building contract =buildingMapper.selectBuildingNoByUid(egress.getUid());
        edu.ysu.backend.entity.dto.Bed bedUpdate= edu.ysu.backend.entity.dto.Bed.builder()
                .eid(contract.getEid())
                .statue(1).build();
        bedMapper.updateById(bedUpdate);
        int updated = egressMapper.updateById(update);
        return updated>0?Result.ok("登记成功"):Result.fail("登记失败");
    }

    @Override
    public Result getCustomerInfoRecordPaged(String keyword, int pageNum, int pageSize) {
        Page<edu.ysu.backend.entity.dto.Customer> page = new Page<>(pageNum, pageSize);
        QueryWrapper<edu.ysu.backend.entity.dto.Customer> queryWrapper = new QueryWrapper<>();
        if (keyword==null||"".equals(keyword)){
            queryWrapper.eq("del",0);
        }else {
            queryWrapper.like("name",keyword).and(condition->condition.eq("del",0));
        }
        IPage<edu.ysu.backend.entity.dto.Customer> iPage = customerMapper.selectPage(page, queryWrapper);
        for (edu.ysu.backend.entity.dto.Customer customer : iPage.getRecords()){
            edu.ysu.backend.entity.dto.Building building=buildingMapper.selectCustomerInWhere(customer.getUid());
            customer.setBuildingObj(building);
            customer.setSupport(serviceMapper.selectSupportName(customer.getUid()));
            customer.setButler(serviceMapper.selectButlerByUid(customer.getUid()));
            customer.setFamilies(customerMapper.selectFamilyByUidLimited(customer.getUid()));
            customer.setEmergencies(customerMapper.selectEmergencyByUidLimited(customer.getUid()));
        }
        return Result.ok("查询客户信息记录成功").data("recordP",iPage);
    }

    @Override
    public Result getFamilyRecordPaged(int uid, int pageNum) {
        Page<edu.ysu.backend.entity.dto.Family> page = new Page<>(pageNum, 5);
        QueryWrapper<edu.ysu.backend.entity.dto.Family> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        IPage<edu.ysu.backend.entity.dto.Family> iPage=familySetMapper.selectPage(page,queryWrapper);
        return Result.ok("查询家庭成员信息成功").data("recordP",iPage);
    }

    @Override
    public Result getEmergencyRecordPaged(int uid, int pageNum) {
        Page<edu.ysu.backend.entity.dto.Emergency> page=new Page<>(pageNum,5);
        QueryWrapper<edu.ysu.backend.entity.dto.Emergency> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        IPage<edu.ysu.backend.entity.dto.Emergency> iPage=emergencySetMapper.selectPage(page,queryWrapper);
        return Result.ok("查询紧急联系人信息成功").data("recordP",iPage);
    }

    @Override
    public Result getButlerRecordPaged(int uid, int pageNum) {
        Page<Staff> page=new Page<>(pageNum,5);
        List<Integer> sids=serviceMapper.selectButlerId(uid);
        QueryWrapper<Staff> queryWrapper=new QueryWrapper<>();
        if (sids.isEmpty()){
            queryWrapper.eq("sid",-1);
        }else {
            queryWrapper.in("sid",sids);
        }
        IPage<Staff> iPage=staffMapper.selectPage(page,queryWrapper);
        return Result.ok("查询健康管家信息成功").data("recordP",iPage);
    }

    @Override
    public Result getSupportRecordPaged(int uid, int pageNum) {
        Page<Staff> page=new Page<>(pageNum,5);
        List<Integer> sids=serviceMapper.selectSupportId(uid);
        QueryWrapper<Staff> queryWrapper=new QueryWrapper<>();
        if (sids.isEmpty()){
            queryWrapper.eq("sid",-1);
        }else {
            queryWrapper.in("sid",sids);
        }
        IPage<Staff> iPage=staffMapper.selectPage(page,queryWrapper);
        return Result.ok("查询护工信息成功").data("recordP",iPage);
    }

    @Override
    public Result addFamilyRecord(Family family) {
        QueryWrapper<edu.ysu.backend.entity.dto.Family> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",family.getName()).and(condition->condition.eq("uid",family.getUid()));
        List<edu.ysu.backend.entity.dto.Family> exist=familySetMapper.selectList(queryWrapper);
        if (exist.isEmpty()){
            edu.ysu.backend.entity.dto.Family inset= edu.ysu.backend.entity.dto.Family.builder()
                    .uid(family.getUid())
                    .name(family.getName())
                    .age(family.getAge())
                    .gander(family.getGander())
                    .relation(family.getRelation())
                    .id(family.getId())
                    .address(family.getAddress())
                    .tel(family.getTel()).build();
            int insert = familySetMapper.insert(inset);
            return insert>0?Result.ok("添加成功"):Result.fail("添加失败");
        }else {
            return Result.fail("改客户已登记该成员信息").data("err",500);
        }
    }

    @Override
    public Result deleteFamilyRecord(Family family) {
        edu.ysu.backend.entity.dto.Family del= edu.ysu.backend.entity.dto.Family.builder().fid(family.getFid()).build();
        int delete=familySetMapper.deleteById(del);
        return delete>0?Result.ok("删除成功"):Result.fail("删除失败");
    }

    @Override
    public Result changeFamilyRecord(Family family) {
        QueryWrapper<edu.ysu.backend.entity.dto.Family> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",family.getUid());
        edu.ysu.backend.entity.dto.Family exist=familySetMapper.selectOne(queryWrapper);
        if (Objects.equals(exist.getName(),family.getName())){
            edu.ysu.backend.entity.dto.Family update= edu.ysu.backend.entity.dto.Family.builder()
                    .fid(family.getFid())
                    .name(family.getName())
                    .age(family.getAge())
                    .gander(family.getGander())
                    .relation(family.getRelation())
                    .id(family.getId())
                    .address(family.getAddress())
                    .tel(family.getTel()).build();
            int updated = familySetMapper.updateById(update);
            return updated>0?Result.ok("修改成功"):Result.fail("修改失败");
        }else {
            QueryWrapper<edu.ysu.backend.entity.dto.Family> familyQueryWrapper=new QueryWrapper<>();
            familyQueryWrapper.eq("name",family.getName()).and(condition->condition.eq("uid",family.getUid()));
            List<edu.ysu.backend.entity.dto.Family> isExist=familySetMapper.selectList(familyQueryWrapper);
            if (isExist.isEmpty()){
                edu.ysu.backend.entity.dto.Family update= edu.ysu.backend.entity.dto.Family.builder()
                        .fid(family.getFid())
                        .name(family.getName())
                        .age(family.getAge())
                        .gander(family.getGander())
                        .relation(family.getRelation())
                        .id(family.getId())
                        .address(family.getAddress())
                        .tel(family.getTel()).build();
                int updated = familySetMapper.updateById(update);
                return updated>0?Result.ok("修改成功"):Result.fail("修改失败");
            }else {
                return Result.fail("该客户已登记该成员信息").data("err",500);
            }
        }
    }

    @Override
    public Result addEmergencyRecord(Emergency emergency) {
        QueryWrapper<edu.ysu.backend.entity.dto.Emergency> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",emergency.getUid()).eq("name",emergency.getName());
        List<edu.ysu.backend.entity.dto.Emergency> exist=emergencySetMapper.selectList(queryWrapper);
        if (exist.isEmpty()){
            edu.ysu.backend.entity.dto.Emergency inset= edu.ysu.backend.entity.dto.Emergency.builder()
                    .uid(emergency.getUid())
                    .name(emergency.getName())
                    .age(emergency.getAge())
                    .gander(emergency.getGander())
                    .relation(emergency.getRelation())
                    .id(emergency.getId())
                    .address(emergency.getAddress())
                    .tel(emergency.getTel()).build();
            int insert=emergencySetMapper.insert(inset);
            return insert>0?Result.ok("添加成功"):Result.fail("添加失败");
        }else {
            return Result.fail("改客户已登记该成员信息").data("err",500);
        }
    }

    @Override
    public Result deleteEmergencyRecord(Emergency emergency) {
        edu.ysu.backend.entity.dto.Emergency del= edu.ysu.backend.entity.dto.Emergency.builder().eid(emergency.getEid()).build();
        int delete=emergencySetMapper.deleteById(del);
        return delete>0?Result.ok("删除成功"):Result.fail("删除失败");
    }

    @Override
    public Result changeEmergencyRecord(Emergency emergency) {
        QueryWrapper<edu.ysu.backend.entity.dto.Emergency> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",emergency.getUid());
        edu.ysu.backend.entity.dto.Emergency exist=emergencySetMapper.selectOne(queryWrapper);
        if (Objects.equals(exist.getName(),emergency.getName())){
            edu.ysu.backend.entity.dto.Emergency update= edu.ysu.backend.entity.dto.Emergency.builder()
                    .eid(emergency.getEid())
                    .name(emergency.getName())
                    .age(emergency.getAge())
                    .gander(emergency.getGander())
                    .relation(emergency.getRelation())
                    .id(emergency.getId())
                    .address(emergency.getAddress())
                    .tel(emergency.getTel()).build();
            int updated=emergencySetMapper.updateById(update);
            return updated>0?Result.ok("修改成功"):Result.fail("修改失败");
        }else {
            QueryWrapper<edu.ysu.backend.entity.dto.Emergency> emergencyQueryWrapper=new QueryWrapper<>();
            emergencyQueryWrapper.eq("name",emergency.getName()).and(condition->condition.eq("uid",emergency.getUid()));
            List<edu.ysu.backend.entity.dto.Emergency> isExist=emergencySetMapper.selectList(emergencyQueryWrapper);
            if (isExist.isEmpty()){
                edu.ysu.backend.entity.dto.Emergency update= edu.ysu.backend.entity.dto.Emergency.builder()
                        .eid(emergency.getEid())
                        .name(emergency.getName())
                        .age(emergency.getAge())
                        .gander(emergency.getGander())
                        .relation(emergency.getRelation())
                        .id(emergency.getId())
                        .address(emergency.getAddress())
                        .tel(emergency.getTel()).build();
                int updated=emergencySetMapper.updateById(update);
                return updated>0?Result.ok("修改成功"):Result.fail("修改失败");
            }else {
                return Result.fail("该客户已登记该成员信息").data("err",500);
            }
        }
    }

    @Override
    public Result addButlerRecord(edu.ysu.backend.entity.vo.User user) {
        Integer exist=serviceMapper.selectButlerExist(user.getSid(), user.getUid());
        if (exist==0){
            Integer insert = serviceMapper.insertServiceSet(user.getSid(), user.getUid());
            return insert>0?Result.ok("添加成功"):Result.fail("添加失败");
        }else {
            return Result.fail("该管家已被选取").data("err",500);
        }
    }

    @Override
    public Result addSupportRecord(edu.ysu.backend.entity.vo.User user) {
        Integer exist=serviceMapper.selectSupportExist(user.getSid(), user.getUid());
        if (exist==0){
            Integer insert = serviceMapper.insertSupportSet(user.getSid(), user.getUid());
            return insert>0?Result.ok("添加成功"):Result.fail("添加失败");
        }else {
            return Result.fail("该护工已被选取").data("err",500);
        }
    }

    @Override
    public Result deleteButlerRecord(edu.ysu.backend.entity.vo.User user) {
        Integer delete=serviceMapper.deleteServiceSetRecord(user.getSid(), user.getUid());
        return delete>0?Result.ok("删除成功"):Result.fail("删除失败");
    }

    @Override
    public Result deleteSupportRecord(edu.ysu.backend.entity.vo.User user) {
        Integer delete=serviceMapper.deleteSupportSetRecord(user.getSid(), user.getUid());
        return delete>0?Result.ok("删除成功"):Result.fail("删除失败");
    }
}
