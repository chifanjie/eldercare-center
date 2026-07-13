package edu.ysu.backend.service;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Meal;
import edu.ysu.backend.entity.vo.MealManage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface MealService {

    Result getMealManagePaged(String keyword,int pageNum,int pageSize);

    Result changeMealManage(MealManage mealManage);

    Result getMealManageSetInfo(Integer cid);

    Result setMealManage(MealManage mealManage);

    Result getMealPaged(String round);

    Result deleteMeal(Meal meal);

    Result uploadResult(MultipartFile food);

    Result addMealRecord(Meal meal);

    Result getMealInfo(Integer mid);

    Result changeMealRecord(Meal meal);

    Result setMealRecord(Meal meal);

    Result getMealStatistics(String keyword,String round,int pageNum,int pageSize);

    Result getMealStatisticsInfo(Integer mid,Integer week,Integer time);

    Result setMealStatistics(Meal meal);


}
