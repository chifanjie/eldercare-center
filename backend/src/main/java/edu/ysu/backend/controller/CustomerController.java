package edu.ysu.backend.controller;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.*;
import edu.ysu.backend.service.CustomerService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "客户信息数据接口")
@RequestMapping("/customer")
// 与客户信息数据有关的接口
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /***
     * 入住登记记录分页查询接口
     * @param keyword 关键词
     * @param round 客户类型
     * @param pageNum 当前页
     * @param pageSize 每页显示条数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/customer_paged")
    public Result getCustomerPaged(String keyword,String round,int pageNum,int pageSize){
        return customerService.getCustomerPaged(keyword,round,pageNum,pageSize);
    }

    /***
     * 添加入住登记接口
     * @param customer 客户对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_checkin")
    public Result addCustomerCheckin(@RequestBody Customer customer){
        return customerService.addCheckin(customer);
    }

    /***
     * 删除入住登记接口（未启用）
     * @param customer 客户对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/delete_checkin")
    public Result deleteCustomerCheckin(@RequestBody Customer customer){
        return customerService.deleteCheckin(customer);
    }

    /***
     * 查询客户位置接口
     * @param uid 顾客id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/where_is_customer")
    public Result getWhereCustomerIn(Integer uid){
        return customerService.getCustomerPosition(uid);
    }

    /***
     * 修改入住登记接口
     * @param customer 客户对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_checkin")
    public Result changeCustomerCheckin(@RequestBody Customer customer){
        return customerService.changeCustomerCheckin(customer);
    }

    /***
     * 退住记录分页查询接口
     * @param keyword 关键词
     * @param pageNum 当前页
     * @param pageSize 每页显示条数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/checkout_paged")
    public Result getCheckoutPaged(String keyword,int pageNum,int pageSize){
        return customerService.getCheckoutPaged(keyword,pageNum,pageSize);
    }

    /***
     * 添加退住记录接口
     * @param checkout 退住记录对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_checkout")
    public Result addCheckout(@RequestBody Checkout checkout){
        return customerService.addCheckoutRecord(checkout);
    }

    /***
     * 删除退住记录接口
     * @param checkout 退住记录对象
     * @return 详情请见knife4j接口文档
     */
    @DeleteMapping("/delete_checkout")
    public Result deleteCustomerCheckout(@RequestBody Checkout checkout){
        return customerService.deleteCheckout(checkout);
    }

    /***
     * 审批退住记录接口
     * @param checkout 退住记录对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/approve_checkout")
    public Result approveCheckout(@RequestBody Checkout checkout){
        return customerService.approveCheckout(checkout);
    }

    /***
     * 修改退住记录接口
     * @param checkout 退住记录对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_checkout")
    public Result changeCheckout(@RequestBody Checkout checkout){
        return customerService.changeCheckout(checkout);
    }

    /***
     * 客户外出记录分页查询接口
     * @param keyword 关键词
     * @param pageNum 当前页
     * @param pageSize 每页显示条数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/customer_egress_paged")
    public Result getCustomerEgressPaged(String keyword,int pageNum,int pageSize){
        return customerService.getCustomerEgressPaged(keyword,pageNum,pageSize);
    }

    /***
     * 审批客户外出记录接口
     * @param egress 外出记录对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/approve_egress")
    public Result approveEgress(@RequestBody List<Egress> egress){
        return customerService.approveEgress(egress);
    }

    /***
     * 删除客户外出记录接口
     * @param egress 外出记录对象
     * @return 详情请见knife4j接口文档
     */
    @DeleteMapping("/delete_egress")
    public Result deleteEgress(@RequestBody Egress egress){
        return customerService.deleteEgress(egress);
    }

    /***
     * 添加客户外出记录接口
     * @param egress 外出记录对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_egress")
    public Result addEgressRecord(@RequestBody Egress egress){
        return customerService.addEgressRecord(egress);
    }

    /***
     * 修改客户外出记录接口
     * @param egress 外出记录对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_egress")
    public Result changeEgressRecord(@RequestBody Egress egress){
        return customerService.changeEgress(egress);
    }

    /***
     * 登记实际回院时间接口
     * @param egress 外出记录对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/add_really")
    public Result addEgressReally(@RequestBody Egress egress){
        return customerService.addEgressReallyRecord(egress);
    }

    /***
     * 客户信息分页查询接口
     * @param keyword 关键词
     * @param pageNum 当前页
     * @param pageSize 每页显示条数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/customer_info_record_paged")
    public Result getCustomerInfoRecordPaged(String keyword,int pageNum,int pageSize){
        return customerService.getCustomerInfoRecordPaged(keyword,pageNum,pageSize);
    }

    /***
     * 家庭成员信息分页查询接口
     * @param uid 顾客id
     * @param pageNum 当前页
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/family_paged")
    public Result getFamilyPaged(int uid,int pageNum){
        return customerService.getFamilyRecordPaged(uid,pageNum);
    }

    /***
     * 紧急联系人信息分页查询接口
     * @param uid 顾客id
     * @param pageNum 当前页
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/emergency_paged")
    public Result getEmergencyPaged(int uid,int pageNum){
        return customerService.getEmergencyRecordPaged(uid,pageNum);
    }

    /***
     * 所绑定的健康管家信息分页查询接口
     * @param uid 顾客id
     * @param pageNum 当前页
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/butler_paged")
    public Result getButlerPaged(int uid,int pageNum){
        return customerService.getButlerRecordPaged(uid,pageNum);
    }

    /***
     * 所绑定的护工信息分页查询接口
     * @param uid 顾客id
     * @param pageNum 当前页
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/support_paged")
    public Result getSupportPaged(int uid,int pageNum){
        return customerService.getSupportRecordPaged(uid,pageNum);
    }

    /***
     * 添加家庭成员接口
     * @param family 家庭成员对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_family")
    public Result addFamilyRecord(@RequestBody Family family){
        return customerService.addFamilyRecord(family);
    }

    /***
     * 添加紧急联系人接口
     * @param emergency 紧急联系人对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_emergency")
    public Result addEmergencyRecord(@RequestBody Emergency emergency){
        return customerService.addEmergencyRecord(emergency);
    }

    /***
     * 绑定健康管家接口
     * @param user 用户对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_butler")
    public Result addButlerRecord(@RequestBody User user){
        return customerService.addButlerRecord(user);
    }

    /***
     * 绑定护工接口
     * @param user 用户对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_support")
    public Result addSupportRecord(@RequestBody User user){
        return customerService.addSupportRecord(user);
    }

    /***
     * 修改家庭成员接口
     * @param family 家庭成员对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_family")
    public Result changeFamilyRecord(@RequestBody Family family){
        return customerService.changeFamilyRecord(family);
    }

    /***
     * 修改紧急联系人接口
     * @param emergency 紧急联系人对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_emergency")
    public Result changeEmergencyRecord(@RequestBody Emergency emergency){
        return customerService.changeEmergencyRecord(emergency);
    }

    /***
     * 删除关联健康管家接口
     * @param family 家庭成员对象
     * @return 详情请见knife4j接口文档
     */
    @DeleteMapping("/delete_family")
    public Result deleteFamilyRecord(@RequestBody Family family){
        return customerService.deleteFamilyRecord(family);
    }

    /***
     * 删除关联紧急联系人接口
     * @param emergency 紧急联系人对象
     * @return 详情请见knife4j接口文档
     */
    @DeleteMapping("/delete_emergency")
    public Result deleteEmergencyRecord(@RequestBody Emergency emergency){
        return customerService.deleteEmergencyRecord(emergency);
    }

    /***
     * 删除关联健康管家接口
     * @param user 用户对象
     * @return 详情请见knife4j接口文档
     */
    @DeleteMapping("/delete_butler")
    public Result deleteButlerRecord(@RequestBody User user){
        return customerService.deleteButlerRecord(user);
    }

    /***
     * 删除关联护工接口
     * @param user 用户对象
     * @return 详情请见knife4j接口文档
     */
    @DeleteMapping("/delete_support")
    public Result deleteSupportRecord(@RequestBody User user){
        return customerService.deleteSupportRecord(user);
    }

}
