package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.Meal;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MealMapper extends BaseMapper<Meal> {

    @Select("SELECT meal.mid,meal.name,food,tid,type,meal_week_contrast.time,price,muslim,date,del,memo,meal_week_contrast.week " +
            "FROM meal,meal_week_contrast " +
            "WHERE meal.mid=meal_week_contrast.mid " +
            "AND meal_week_contrast.week=#{week} " +
            "AND meal.del=0")
    List<Meal> selectMealRecord(Integer week);

    @Select("SELECT meal_tag.name AS tagName " +
            "FROM meal_tag " +
            "WHERE meal_tag.tid=#{tid} ")
    String selectTagName(Integer tid);

    @Select("SELECT meal_type.name AS typeName "+
            "FROM meal_type " +
            "WHERE meal_type.tid=#{tid}")
    String selectTypeName(Integer tid);

    @Select("SELECT tid AS typeValue,name AS typeName FROM meal_type WHERE del=0")
    List<Meal> selectTypeList();

    @Select("SELECT tid AS tagValue,name AS tagName FROM meal_tag WHERE del=0")
    List<Meal> selectTagList();

    @Insert("INSERT INTO meal_week_contrast(mid,week,time) VALUES(#{mid},#{week},#{time})")
    Integer insertIntoContrast(@Param("mid") Integer mid,@Param("week") Integer week,@Param("time") Integer time);

    @Update("UPDATE meal_week_contrast SET name=#{name},position=#{position} WHERE mid=#{mid} AND week=#{week} AND time=#{time}")
    Integer updateContrast(@Param("mid") Integer mid,@Param("week") Integer week,@Param("time") Integer time,@Param("name") String name,@Param("position") String position);

    @Select("SELECT COUNT(*) FROM meal_week_contrast WHERE mid=#{mid} AND week=#{week} AND time=#{time}")
    Integer selectIsInContrast(@Param("mid") Integer mid,@Param("week") Integer week,@Param("time") Integer time);

    @Delete("DELETE FROM meal_week_contrast WHERE mid=#{mid} AND week=#{week} AND time=#{time}")
    Integer deleteContrast(@Param("mid") Integer mid,@Param("week") Integer week,@Param("time") Integer time);

    @Select("SELECT mid FROM meal_week_contrast WHERE week=#{week} AND time=#{time}")
    List<Integer> selectContrast(@Param("week") Integer week,@Param("time") Integer time);

    @Select("SELECT * FROM meal_week_contrast WHERE mid=#{mid} AND week=#{week} AND time=#{time}")
    Meal selectContrastInfo(@Param("mid") Integer mid,@Param("week") Integer week,@Param("time") Integer time);

}
