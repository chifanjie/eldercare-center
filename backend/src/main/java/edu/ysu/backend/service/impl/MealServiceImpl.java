package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.ysu.backend.entity.dto.Customer;
import edu.ysu.backend.entity.pojo.OnlineContext;
import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.mapper.*;
import edu.ysu.backend.service.MealService;
import edu.ysu.backend.util.FileUtil;
import edu.ysu.backend.util.JwtUtil;
import edu.ysu.backend.entity.dto.User;
import edu.ysu.backend.entity.vo.Meal;
import edu.ysu.backend.entity.vo.MealManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealManageMapper mealManageMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private MealMapper mealMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ServiceMapper serviceMapper;
    @Value("${resources.food}")
    private String foodPath;

    @Override
    public Result getMealManagePaged(String keyword, int pageNum, int pageSize) {
        String current = OnlineContext.getCurrent();
        String email = JwtUtil.parseJWT(current).getSubject();
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("email",email));
        Page<edu.ysu.backend.entity.dto.MealManage> page=new Page<>(pageNum,pageSize);
        IPage<edu.ysu.backend.entity.dto.MealManage> iPage;
        if (user.getRole()==1){
            QueryWrapper<edu.ysu.backend.entity.dto.MealManage> queryWrapper=new QueryWrapper<>();
            if (keyword==null||"".equals(keyword)){
                queryWrapper=null;
            }else {
                QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                customerQueryWrapper.like("name",keyword);
                List<Customer> customers=customerMapper.selectList(customerQueryWrapper);
                Set<Integer> uidList=new HashSet<>();
                if (!customers.isEmpty()){
                    for (Customer customer:customers){
                        uidList.add(customer.getUid());
                    }
                    queryWrapper.in("uid",uidList);
                }else {
                    queryWrapper.eq("uid",-1);
                }
            }
            iPage=mealManageMapper.selectPage(page,queryWrapper);
        }else {
            List<Integer> uidList=serviceMapper.selectButlerProvider(user.getUid());
            QueryWrapper<edu.ysu.backend.entity.dto.MealManage> queryWrapper=new QueryWrapper<>();
            if (!uidList.isEmpty()){
                if (keyword==null||"".equals(keyword)){
                    queryWrapper.in("uid",uidList);
                }else {
                    QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
                    customerQueryWrapper.like("name",keyword).and(condition->condition.in("uid",uidList));
                    List<Customer> customers=customerMapper.selectList(customerQueryWrapper);
                    Set<Integer> uidSet=new HashSet<>();
                    if (!customers.isEmpty()){
                        for (Customer customer:customers){
                            uidSet.add(customer.getUid());
                        }
                        queryWrapper.in("uid",uidSet);
                    }else {
                        queryWrapper.eq("uid",-1);
                    }
                }
            }else {
                queryWrapper.eq("uid",-1);
            }
            iPage=mealManageMapper.selectPage(page,queryWrapper);
        }
        for (edu.ysu.backend.entity.dto.MealManage manage: iPage.getRecords()){
            QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
            customerQueryWrapper.eq("uid",manage.getUid());
            Customer customer=customerMapper.selectOne(customerQueryWrapper);
            manage.setName(customer.getName());
            manage.setAge(customer.getAge());
            manage.setGander(customer.getGander());
        }
        return Result.ok("查询膳食管理记录成功").data("recordP",iPage);
    }

    @Override
    public Result changeMealManage(MealManage mealManage) {
        edu.ysu.backend.entity.dto.MealManage update= edu.ysu.backend.entity.dto.MealManage.builder()
                .uid(mealManage.getUid())
                .habit(mealManage.getHabit())
                .warn(mealManage.getWarn())
                .memo(mealManage.getMemo()).build();
        int updated = mealManageMapper.updateById(update);
        return updated>0?Result.ok("修改膳食管理记录成功"):Result.fail("修改膳食管理记录失败");
    }

    @Override
    public Result getMealManageSetInfo(Integer cid) {
        return Result.ok("查询膳食客户对应关系成功").data("setInfo",mealManageMapper.selectMealCustomer(cid));
    }

    @Override
    public Result setMealManage(MealManage mealManage) {
        int updated=0;
        if (mealManage.getMid().isEmpty()){
            mealManageMapper.deleteMealCustomerContrastAll(mealManage.getCid());
            return Result.ok("设置膳食成功!");
        }else {
            Integer delete = mealManageMapper.deleteMealCustomerContrastAll(mealManage.getCid());
            for (Integer mid:mealManage.getMid()){
                updated = mealManageMapper.insertMealCustomerContrast(mealManage.getCid(),mid);
            }
            return updated>0?Result.ok("设置膳食成功!"):Result.fail("设置膳食失败!");
        }
    }

    @Override
    public Result getMealPaged(String round) {
        List<edu.ysu.backend.entity.dto.Meal> mealList=mealMapper.selectMealRecord(Integer.valueOf(round));
        for (edu.ysu.backend.entity.dto.Meal meal:mealList){
            meal.setFood(foodPath+meal.getFood());
            meal.setTagName(mealMapper.selectTagName(meal.getTid()));
            meal.setTypeName(mealMapper.selectTypeName(meal.getType()));
        }
        return Result.ok("查询膳食日历记录成功").data("recordP",mealList);
    }

    @Override
    public Result deleteMeal(Meal meal) {
        int delete= mealMapper.deleteContrast(meal.getMid(),meal.getWeek(), meal.getTime());
        return delete>0?Result.ok("删除膳食日历记录成功"):Result.fail("删除膳食日历记录失败");
    }

    @Override
    public Result uploadResult(MultipartFile food) {
        Result res = FileUtil.saveFile(food, "food");
        if (res.getCode()==200){
            return Result.ok("上传膳食图片成功").data("food",res.getData());
        }else {
            return Result.fail("上传膳食图片失败");
        }
    }

    @Override
    public Result addMealRecord(Meal meal) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date=LocalDate.now();
        String nowDate=date.format(formatter);
        edu.ysu.backend.entity.dto.Meal insert= edu.ysu.backend.entity.dto.Meal.builder()
                .name(meal.getName())
                .food(meal.getFood()==null||"".equals(meal.getFood())?"default.jpg":meal.getFood())
                .tid(meal.getTid())
                .type(meal.getType())
                .price(meal.getPrice())
                .num(meal.getNum())
                .muslim(meal.getMuslim())
                .date(nowDate)
                .memo(meal.getMemo()).build();
        int inserted = mealMapper.insert(insert);
        if (inserted>0){
            Integer in = mealMapper.insertIntoContrast(insert.getMid(), meal.getWeek(), meal.getTime());
            return in>0?Result.ok("插入膳食日记成功"):Result.fail("插入膳食日记失败");
        }else {
            return Result.fail("插入膳食日记失败");
        }
    }

    @Override
    public Result getMealInfo(Integer mid) {
        QueryWrapper<edu.ysu.backend.entity.dto.Meal> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mid",mid);
        edu.ysu.backend.entity.dto.Meal meal = mealMapper.selectOne(queryWrapper);
        meal.setFood(foodPath+meal.getFood());
        return Result.ok("查询膳食日记成功").data("mealRecord",meal);
    }

    @Override
    public Result changeMealRecord(Meal meal) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date=LocalDate.now();
        String nowDate=date.format(formatter);
        edu.ysu.backend.entity.dto.Meal update= edu.ysu.backend.entity.dto.Meal.builder()
                .mid(meal.getMid())
                .name(meal.getName())
                .food(meal.getFood()==null||"".equals(meal.getFood())?"default.jpg":meal.getFood())
                .tid(meal.getTid())
                .type(meal.getType())
                .price(meal.getPrice())
                .num(meal.getNum())
                .muslim(meal.getMuslim())
                .date(nowDate)
                .memo(meal.getMemo()).build();
        mealMapper.deleteContrast(meal.getMid(), meal.getWeek(), meal.getTime());
        mealMapper.deleteContrast(meal.getMid(), meal.getBeforeWeek(), meal.getBeforeTime());
        Integer updateContrast = mealMapper.insertIntoContrast(meal.getMid(), meal.getWeek(), meal.getTime());
        int updated = mealMapper.updateById(update);
        return updateContrast>0&&updated>0?Result.ok("修改膳食日记成功"):Result.fail("修改膳食日记失败");
    }

    @Override
    public Result setMealRecord(Meal meal) {
        Integer exist = mealMapper.selectIsInContrast(meal.getMid(), meal.getWeek(), meal.getTime());
        if (exist!=0){
            return Result.fail("在所选的日期或餐时存在该膳食");
        }else {
            Integer insert = mealMapper.insertIntoContrast(meal.getMid(), meal.getWeek(), meal.getTime());
            if (insert!=0){
                return Result.ok("添加膳食成功");
            }else {
                return Result.fail("添加膳食失败");
            }
        }
    }

    @Override
    public Result getMealStatistics(String keyword, String round, int pageNum, int pageSize) {
        QueryWrapper<edu.ysu.backend.entity.dto.Meal> queryWrapper=new QueryWrapper<>();
        Page<edu.ysu.backend.entity.dto.Meal> page=new Page<>(pageNum,pageSize);
        IPage<edu.ysu.backend.entity.dto.Meal> mealIPage;
        List<Integer> mids=mealMapper.selectContrast(Integer.valueOf(keyword), Integer.valueOf(round));
        if (mids.isEmpty()){
            queryWrapper.eq("mid",-1);
            mealIPage = mealMapper.selectPage(page, queryWrapper);
        }else {
            Set<Integer> set = new HashSet<>(mids);
            queryWrapper.in("mid",set);
            mealIPage = mealMapper.selectPage(page, queryWrapper);
            for (edu.ysu.backend.entity.dto.Meal meal:mealIPage.getRecords()){
                meal.setTypeName(mealMapper.selectTypeName(meal.getType()));
                meal.setWeek(Integer.valueOf(keyword));
                meal.setTime(Integer.valueOf(round));
            }
        }
        return Result.ok("查询膳食统计成功").data("recordP",mealIPage);
    }

    @Override
    public Result getMealStatisticsInfo(Integer mid, Integer week, Integer time) {
        edu.ysu.backend.entity.dto.Meal meal=mealMapper.selectContrastInfo(mid, week, time);
        QueryWrapper<edu.ysu.backend.entity.dto.Meal> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mid",mid);
        meal.setMemo(mealMapper.selectOne(queryWrapper).getMemo());
        return Result.ok("查询膳食统计信息成功").data("mealInfo",meal);
    }

    @Override
    public Result setMealStatistics(Meal meal) {
        int updateMeal=0;
        Integer updated=mealMapper.updateContrast(meal.getMid(),meal.getWeek(),meal.getTime(), meal.getName(), meal.getPosition());
        if (updated>0){
            edu.ysu.backend.entity.dto.Meal update= edu.ysu.backend.entity.dto.Meal.builder().memo(meal.getMemo()).mid(meal.getMid()).build();
            updateMeal = mealMapper.updateById(update);
            return updateMeal>0?Result.ok("更新膳食统计信息成功"):Result.fail("更新膳食统计信息失败");
        }else {
            return Result.fail("更新膳食统计信息失败");
        }
    }

}
