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
public class NurseRecord {
    @TableId(value = "nid",type = IdType.AUTO)
    private Integer nid;
    private Integer sid;
    private Integer uid;
    private Integer cid;
    private Integer time;
    private String date;
    private String memo;
    private Integer strategy;
    @TableField(exist = false)
    private String customerName;
    @TableField(exist = false)
    private String staffName;
    @TableField(exist = false)
    private String contentName;
}
