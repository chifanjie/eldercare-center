package edu.ysu.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MealManage {
    @TableId(value = "uid",type = IdType.INPUT)
    private Integer uid;
    private String habit;
    private String warn;
    private String memo;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String gander;
    @TableField(exist = false)
    private Integer age;
    @TableField(exist = false)
    private Integer cid;
    @TableField(exist = false)
    private Integer mid;

}
