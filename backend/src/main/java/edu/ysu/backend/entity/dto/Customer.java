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
public class Customer {
    @TableId(value = "uid",type = IdType.AUTO)
    private Integer uid;
    private String file;
    private String name;
    private String gander;
    private Integer age;
    private String id;
    private String checkin;
    private String expiration;
    private String tel;
    private String memo;
    private Integer statue;
    private Integer del;
    @TableField(exist = false)
    private String family;
    @TableField(exist = false)
    private String accompany;
    @TableField(exist = false)
    private String assess;
    @TableField(exist = false)
    private String responsible;
    @TableField(exist = false)
    private List<String> support;
    @TableField(exist = false)
    private List<String> butler;
    @TableField(exist = false)
    private String room;
    @TableField(exist = false)
    private String building;
    @TableField(exist = false)
    private String bedInfo;
    @TableField(exist = false)
    private Building buildingObj;
    @TableField(exist = false)
    private List<Family> families;
    @TableField(exist = false)
    private List<Emergency> emergencies;
    @TableField(exist = false)
    private Emergency emergency;
    @TableField(exist = false)
    private Bed bed;
}
