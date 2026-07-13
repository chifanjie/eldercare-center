package edu.ysu.backend.controller;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Service;
import edu.ysu.backend.entity.vo.Staff;
import edu.ysu.backend.service.ServicesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "服务管理数据接口")
@RequestMapping("/service")
// 与服务管理数据有关的接口
public class ServiceSetController {

    @Autowired
    private ServicesService servicesService;

    /***
     * 服务关注客户列表记录分页查询接口
     * @param keyword 关键词
     * @param pageNum 当前页
     * @param pageSize 每页显示条数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/service_customer_paged")
    public Result getServiceCustomerPaged(String keyword,int pageNum,int pageSize){
        return servicesService.getServiceCustomerPaged(keyword,pageNum,pageSize);
    }

    /***
     * 服务关注已购买护理记录分页查询
     * @param keyword 关键词
     * @param pageNum 当前页
     * @param pageSize 每页显示条数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/service_paged")
    public Result getServicePaged(String keyword,int pageNum,int pageSize){
        return servicesService.getServicePaged(keyword,pageNum,pageSize);
    }

    /***
     * 购买护理服务（添加）数据接口
     * @param service 服务对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_service_purchase")
    public Result addServicePurchase(@RequestBody Service service){
        return servicesService.addServicePurchaseRecord(service);
    }

    /***
     * 护理服务提醒接口
     * @param service 服务对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/alert_service_purchase")
    public Result alertServicePurchase(@RequestBody Service service){
        return servicesService.alertServiceRecord(service);
    }

    /***
     * 护理服务购买接口（指定护理服务购买）
     * @param service 服务对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/add_service_record_purchase")
    public Result addServiceRecordPurchase(@RequestBody Service service){
        return servicesService.addServiceRecordPurchaseRecord(service);
    }

    /***
     * 健康档案获取护理档案接口
     * @param uid 顾客id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/health_data_care")
    public Result getHealthCareData(Integer uid){
        return servicesService.getHealthCareData(uid);
    }

    /***
     * 健康档案获取顾客信息接口
     * @param uid 顾客id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/customer_record")
    public Result getCustomerRecord(Integer uid){
        return servicesService.getCustomerRecord(uid);
    }

    /***
     * 服务对象记录分页查询接口
     * @param keyword 关键词
     * @param pageNum 当前页
     * @param pageSize 每页显示条数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/service_set_paged")
    public Result getServiceSetPaged(String keyword,int pageNum,int pageSize){
        return servicesService.getServiceSetPaged(keyword,pageNum,pageSize);
    }

    /***
     * 取消健康管家关联顾客接口
     * @param service 服务对象
     * @return 详情请见knife4j接口文档
     */
    @DeleteMapping("delete_service_set")
    public Result deleteServiceSet(@RequestBody Service service){
        return servicesService.deleteServiceSet(service);
    }

    /***
     * 修改服务对象信息数据回显接口
     * @param sid 服务对象id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/service_set_info")
    public Result getServiceSetInfo(Integer sid){
        return servicesService.getServiceSetInfo(sid);
    }

    /***
     * 修改服务对象信息数据接口
     * @param staff 员工对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_service_set")
    public Result changeServiceSet(@RequestBody Staff staff){
        return servicesService.changeServiceSet(staff);
    }

}
