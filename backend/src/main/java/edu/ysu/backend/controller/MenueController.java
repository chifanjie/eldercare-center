package edu.ysu.backend.controller;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.service.MenueService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "菜单数据接口")
@RequestMapping("/menue")
// 与菜单相关的接口
public class MenueController {

    @Autowired
    private MenueService menueService;

    /***
     * 获取界面功能菜单接口（根据当前登录用户权限）
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/menue")
    public Result menue(){
        return menueService.getMenue();
    }

    /***
     * 获取角色菜单接口
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/role_menue")
    public Result roleMenue(){
        return menueService.roleMenue();
    }

    /***
     * 获取功能树形菜单接口
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/tree_menue")
    public Result treeMenue(){
        return menueService.getTreeMenue();
    }

    /***
     * 获取护理内容菜单接口
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/nurse_menue")
    public Result nurseMenue(){
        return menueService.getNurseMenue();
    }

    /***
     * 获取床位信息树形菜单接口
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/building_menue")
    public Result buildingMenue(){
        return menueService.getBuildingMenue();
    }

    /***
     * 获取客户菜单接口
     * @param keyword 关键词
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/customer_menue")
    public Result customerMenue(String keyword){
        return menueService.getCustomerMenue(keyword);
    }

    /***
     * 获取员工菜单接口
     * @param keyword 关键词
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/staff_menue")
    public Result staffMenue(String keyword){
        return menueService.getStaffMenue(keyword);
    }

    /***
     * 获取膳食标签菜单接口
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/tag_menue")
    public Result tagMenue(){
        return menueService.getTagMenue();
    }

    /***
     * 获取膳食类别菜单接口
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/type_menue")
    public Result typeMenue(){
        return menueService.getTypeMenue();
    }

    /***
     * 获取膳食菜单接口
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/meal_menue")
    public Result mealMenue(){
        return menueService.getMealMenue();
    }

    /***
     * 获取膳食标签菜单接口（不携带label）
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/meals_tag_menue")
    public Result mealsTagMenue(){
        return menueService.getMealsTagMenue();
    }

    /***
     * 获取护理级别接口
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/level_menue")
    public Result levelMenue(){
        return menueService.getLevelMenue();
    }

    /***
     * 获取健康管家员工菜单接口
     * @param keyword 关键词
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/butler_menue")
    public Result butlerMenue(String keyword){
        return menueService.getButlerMenue(keyword);
    }

    /***
     * 获取护工员工菜单接口
     * @param keyword 关键词
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/support_menue")
    public Result supportMenue(String keyword){
        return menueService.getSupportMenue(keyword);
    }

    /***
     * 获取护理内容菜单接口
     * @param keyword 关键词
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/service_menue")
    public Result serviceMenue(String keyword){
        return menueService.getServiceMenue(keyword);
    }

    /***
     * 根据权限获取顾客菜单
     * @return 详情请见knife4j接口文档
     */
    @GetMapping("/custom_customer_menue")
    public Result customCustomerServiceMenue(String keyword){
        return menueService.customCustomerServiceMenue(keyword);
    }

}
