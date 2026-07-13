package edu.ysu.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    @TableId(value = "mid",type = IdType.AUTO)
    private Integer mid;
    private String name;
    private String food;
    private Integer num;
    private Integer tid;
    private Integer type;
    @TableField(exist = false)
    private Integer time;
    private double price;
    private Integer muslim;
    private String date;
    private Integer del;
    private String memo;
    @TableField(exist = false)
    private Integer week;
    @TableField(exist = false)
    private String tagName;
    @TableField(exist = false)
    private String typeName;
    @TableField(exist = false)
    private Integer tagValue;
    @TableField(exist = false)
    private Integer typeValue;
    @TableField(exist = false)
    private String position;
}
