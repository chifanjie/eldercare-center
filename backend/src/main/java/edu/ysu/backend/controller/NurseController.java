package edu.ysu.backend.controller;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.*;
import edu.ysu.backend.service.NurseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "护理管理数据接口")
@RequestMapping("/nurse")
// 与护理管理数据有关的接口
public class NurseController {

    @Autowired
    private NurseService nurseService;

    /***
     * 护理级别记录分页查询接口
     * @param keyword 关键词
     * @param pageNum 页码
     * @param pageSize 每页显示的记录数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/nurse_level_paged")
    public Result nurseLevelPaged(String keyword,int pageNum,int pageSize){
        return nurseService.getNurseLevelPaged(keyword, pageNum, pageSize);
    }

    /***
     * 护理内容记录分页查询接口
     * @param keyword 关键词
     * @param pageNum 页码
     * @param pageSize 每页显示的记录数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/nurse_content_paged")
    public Result nurseContentPaged(String keyword,int pageNum,int pageSize){
        return nurseService.getNurseContentPaged(keyword, pageNum, pageSize);
    }

    /***
     * 添加护理级别接口
     * @param level 护理级别对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_nurse_level")
    public Result addNewNurseLevel(@RequestBody Level level){
        return nurseService.addNewNurseLevel(level);
    }

    /***
     * 修改护理级别接口
     * @param level 护理级别对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_nurse_level")
    public Result changeNurseLevel(@RequestBody Level level){
        return nurseService.changeNurseLevel(level);
    }

    /***
     * 删除护理级别接口
     * @param level 护理级别对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/delete_nurse_level")
    public Result deleteNurseLevel(@RequestBody Level level){
        return nurseService.deleteNurseLevel(level);
    }

    /***
     * 添加护理内容接口
     * @param content 护理内容对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_nurse_content")
    public Result addNewNurseContent(@RequestBody Content content){
        return nurseService.addNewNurseContent(content);
    }

    /***
     * 修改护理内容接口
     * @param content 护理内容对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_nurse_content")
    public Result changeNurseContent(@RequestBody Content content){
        return nurseService.changeNurseContent(content);
    }

    /***
     * 删除护理内容接口
     * @param content 护理内容对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/delete_nurse_content")
    public Result deleteNurseContent(@RequestBody Content content){
        return nurseService.deleteNurseContent(content);
    }

    /***
     * 护理记录对应的护理内容分页查询功能
     * @param keyword 关键词
     * @param pageNum 页码
     * @param pageSize 每页显示的记录数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/nurse_content_set_paged")
    public Result getNurseContentSetPaged(String keyword,int pageNum,int pageSize){
        return nurseService.getNurseContentSetPaged(keyword,pageNum, pageSize);
    }

    /***
     * 护理级别对应的护理内容添加接口
     * @param levelContent 护理级别对应的护理内容对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_nurse_content_set")
    public Result addNurseContentSet(@RequestBody LevelContent levelContent){
        return nurseService.addNurseContentSet(levelContent);
    }

    /***
     * 护理级别对应的护理内容删除接口
     * @param levelContent 护理级别对应的护理内容对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/delete_nurse_content_set")
    public Result deleteNurseContentSet(@RequestBody LevelContent levelContent){
        return nurseService.deleteNurseContentSet(levelContent);
    }

    /***
     * 护理级别对应的护理内容修改接口
     * @param levelContent 护理级别对应的护理内容对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_nurse_content_set")
    public Result changeNurseContentSet(@RequestBody LevelContent levelContent){
        return nurseService.changeNurseContentSet(levelContent);
    }

    /***
     * 护理记录的客户记录分页查询接口
     * @param keyword 关键词
     * @param pageNum 页码
     * @param pageSize 每页显示的记录数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/nurse_record_customer_paged")
    public Result nurseRecordCustomerPaged(String keyword,int pageNum,int pageSize){
        return nurseService.getCustomerPaged(keyword,pageNum, pageSize);
    }

    /***
     * 护理记录的分页查询接口
     * @param keyword 关键词
     * @param pageNum 页码
     * @param pageSize 每页显示的记录数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/nurse_record_paged")
    public Result nurseRecordPaged(String keyword,int pageNum,int pageSize){
        return nurseService.getNurseRecordPaged(keyword,pageNum, pageSize);
    }

    /***
     * 添加临时护理记录接口
     * @param nurseRecord 护理记录对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_nurse_temp_record")
    public Result addTempNurseRecord(@RequestBody NurseRecord nurseRecord){
        return nurseService.addTempNurseRecord(nurseRecord);
    }

    /***
     * 导出指定时间段的护理记录表格接口
     * @param response HTTP响应对象
     * @param begin 开始时间
     * @param end 结束时间
     */
    @GetMapping("/record_sheet")
    public void requestRecordSheet(HttpServletResponse response, String begin, String end){
        nurseService.requestRecordSheet(response, begin, end);
    }

    /***
     * 今日护理安排接口
     * @param nurseRecord 护理记录对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_group_nurse_record")
    public Result addGroupNurseRecord(@RequestBody NurseRecord nurseRecord){
        return nurseService.addGroupNurseRecord(nurseRecord);
    }

    /***
     * 用户所购买的护理记录分页查询接口
     * @param keyword 关键词
     * @param pageNum 页码
     * @param pageSize 每页显示的记录数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/customer_buy_paged")
    public Result getCustomerBuyServicePaged(String keyword,int pageNum,int pageSize){
        return nurseService.getCustomerBuyServicePaged(keyword,pageNum, pageSize);
    }

    /***
     * 删除用户所购买的护理记录接口
     * @param service 护理记录对象
     * @return 详情请见knife4j接口文档
     */
    @DeleteMapping("/delete_customer_buy_service")
    public Result deleteCustomerBuyService(@RequestBody Service service){
        return nurseService.deleteCustomerBuyService(service);
    }

    /***
     * 修改用户所购买的护理记录接口（只能修改频率）
     * @param service 护理记录对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_customer_buy_service")
    public Result changeCustomerBuyService(@RequestBody Service service){
        return nurseService.changeCustomerBuyService(service);
    }

}
