package edu.ysu.backend.controller;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Exchange;
import edu.ysu.backend.service.BedService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "床位管理数据接口")
@RequestMapping("/bed")
// 与床位信息数据有关的接口
public class BedController {

    @Autowired
    private BedService bedService;

    /***
     * 床位关联记录分页查询接口
     * @param keyword 关键词
     * @param pageNum 当前页码net
     * @param pageSize 每页显示条数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/bed_paged")
    public Result getBedRecordPaged(String keyword,int pageNum,int pageSize){
        return bedService.getBedRecordPaged(keyword, pageNum, pageSize);
    }

    /***
     * 查询床位使用情况接口
     * @param uid 顾客id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/bed_customer")
    public Result getCustomerBedInfo(String uid){
        return bedService.getCustomerBedRecord(uid);
    }

    /***
     * 交换床位接口
     * @param exchange 交换对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/exchange_customer_bed")
    public Result exchangeCustomerBedRecord(@RequestBody Exchange exchange){
        return bedService.exchangeCustomerBedRecord(exchange);
    }

    /***
     * 床位调换接口
     * @param exchange 交换对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/reset_customer_bed")
    public Result resetCustomerRecord(@RequestBody Exchange exchange){
        return bedService.resetCustomerRecord(exchange);
    }

    /***
     * 获取床位信息可视化数据
     * @param round 楼层
     * @param keyword 关键词
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/get_bed_design")
    public Result getBedDesignRecord(String round,String keyword){
        return bedService.getBedDesignRecord(round, keyword);
    }

    /***
     * 获取使用指定床位的用户信息接口（根据uid）
     * @param uid 顾客id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/bed_customer_record")
    public Result getBedCustomer(Integer uid){
        return bedService.getBedCustomer(uid);
    }

}
