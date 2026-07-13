package edu.ysu.backend.controller;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Meal;
import edu.ysu.backend.entity.vo.MealManage;
import edu.ysu.backend.service.MealService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "膳食管理数据接口")
@RequestMapping("/meal")
// 与膳食管理数据相关的接口
public class MealController {

    @Autowired
    private MealService mealService;

    /***
     * 获取膳食管理记录分页查询接口
     * @param keyword 关键词
     * @param pageNum 当前页
     * @param pageSize 每页显示的条数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/meal_manage_paged")
    public Result getMealManagePaged(String keyword,int pageNum,int pageSize){
        return mealService.getMealManagePaged(keyword,pageNum,pageSize);
    }

    /***
     * 修改膳食管理记录接口
     * @param mealManage 膳食管理对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_meal_manage")
    public Result changeMealManage(@RequestBody MealManage mealManage){
        return mealService.changeMealManage(mealManage);
    }

    /***
     * 修改膳食管理记录时数据回显接口
     * @param cid 关联id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/meal_manage_set_info")
    public Result getMealManageSetInfo(Integer cid){
        return mealService.getMealManageSetInfo(cid);
    }

    /***
     * 设置膳食管理记录膳食接口
     * @param mealManage 膳食管理对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/set_meal_manage")
    public Result setMealManage(@RequestBody MealManage mealManage){
        return mealService.setMealManage(mealManage);
    }

    /***
     * 获取膳食日记记录接口
     * @param round 星期几
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/meal_paged")
    public Result getMaelPaged(String round){
        return mealService.getMealPaged(round);
    }

    /***
     * 删除膳食日记记录接口
     * @param meal 膳食对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/delete_meal_record")
    public Result deleteMealRecord(@RequestBody Meal meal){
        return mealService.deleteMeal(meal);
    }

    /***
     * 上传膳食日记记录图片接口
     * @param food 膳食日记记录图片
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/upload_food")
    public Result uploadFood(@RequestBody MultipartFile[] food){
        return mealService.uploadResult(food[0]);
    }

    /***
     * 为某天设置膳食接口
     * @param meal 膳食对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/add_meal_record")
    public Result addMealRecord(@RequestBody Meal meal){
        return mealService.addMealRecord(meal);
    }

    /***
     * 修改膳食日记数据回显接口
     * @param mid 膳食id
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/meal_record_info")
    public Result getMealInfo(Integer mid){
        return mealService.getMealInfo(mid);
    }

    /***
     * 修改膳食日记数据接口
     * @param meal 膳食对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/change_meal_record")
    public Result changeMealRecord(@RequestBody Meal meal){
        return mealService.changeMealRecord(meal);
    }

    /***
     * 设置某日膳食日记数据接口
     * @param meal 膳食对象
     * @return 详情请见knife4j接口文档
     */
    @PostMapping("/set_meal_record")
    public Result setMealRecord(@RequestBody Meal meal){
        return mealService.setMealRecord(meal);
    }

    /***
     * 获取膳食统计分页查询接口
     * @param keyword 关键词
     * @param round 星期几
     * @param pageNum 当前页
     * @param pageSize 每页显示的条数
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/meal_statistics_paged")
    public Result getMealStatisticsPaged(String keyword,String round,int pageNum,int pageSize){
        return mealService.getMealStatistics(keyword, round, pageNum, pageSize);
    }

    /***
     * 获取设置膳食统计数据回显接口
     * @param mid 膳食id
     * @param week 周几
     * @param time 时间
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/meal_statistics_info")
    public Result getMealStatisticsInfo(Integer mid,Integer week,Integer time){
        return mealService.getMealStatisticsInfo(mid, week, time);
    }

    /***
     * 设置膳食统计数据接口
     * @param meal 膳食对象
     * @return 详情请见knife4j接口文档
     */
    @PutMapping("/set_meal_statistics")
    public Result setMealStatistics(@RequestBody Meal meal){
        return mealService.setMealStatistics(meal);
    }

}
