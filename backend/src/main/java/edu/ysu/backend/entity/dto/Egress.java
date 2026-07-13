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
public class Egress {
    @TableId(value = "eid",type = IdType.AUTO)
    private Integer eid;
    private Integer uid;
    private String reason;
    private String egress;
    private String predict;
    private String really;
    private Integer id;
    private String relation;
    private String tel;
    private Integer statue;
    private Integer approver;
    private String approve;
    private String memo;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String gander;
    @TableField(exist = false)
    private Integer age;
    @TableField(exist = false)
    private String file;
    @TableField(exist = false)
    private String relationName;
    @TableField(exist = false)
    private String approverName;
}
